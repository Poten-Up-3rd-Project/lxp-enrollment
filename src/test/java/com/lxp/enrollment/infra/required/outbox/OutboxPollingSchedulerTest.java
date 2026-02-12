package com.lxp.enrollment.infra.required.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.enrollment.application.required.EventProducer;
import com.lxp.enrollment.infra.required.messaging.mapper.EnrollmentEventSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutboxPollingSchedulerTest {

    EnrollmentOutboxRepository outboxRepository;
    EnrollmentEventSerializer serializer;
    EventProducer eventProducer;
    OutboxPollingScheduler scheduler;

    @BeforeEach
    void setUp() {
        outboxRepository = mock(EnrollmentOutboxRepository.class);
        serializer = mock(EnrollmentEventSerializer.class);
        eventProducer = mock(EventProducer.class);
        scheduler = new OutboxPollingScheduler(outboxRepository, serializer, eventProducer);
    }

    @Nested
    @DisplayName("pollAndPublish")
    class PollAndPublish {
        @Test
        void publishes_pending_events_and_marks_published() {
            OutboxEvent outbox = mock(OutboxEvent.class);
            when(outboxRepository.findByStatusAndRetryCountLessThanOrderByOccurredAtAsc(
                eq(OutboxEvent.OutboxStatus.PENDING), anyInt(), anyInt()
            )).thenReturn(List.of(outbox));

            IntegrationEvent event = mock(IntegrationEvent.class);
            when(serializer.deserialize(outbox)).thenReturn(event);

            scheduler.pollAndPublish();

            verify(eventProducer).send(event);
            verify(outbox).markAsPublished();
            verify(outboxRepository).save(outbox);
        }

        @Test
        void marks_failed_when_producer_throws() {
            OutboxEvent outbox = mock(OutboxEvent.class);
            when(outbox.getEventId()).thenReturn("evt-1");
            when(outboxRepository.findByStatusAndRetryCountLessThanOrderByOccurredAtAsc(
                eq(OutboxEvent.OutboxStatus.PENDING), anyInt(), anyInt()
            )).thenReturn(List.of(outbox));

            IntegrationEvent event = mock(IntegrationEvent.class);
            when(serializer.deserialize(outbox)).thenReturn(event);
            doThrow(new RuntimeException("boom")).when(eventProducer).send(event);

            scheduler.pollAndPublish();

            verify(outbox).markAsFailed(contains("boom"));
            verify(outboxRepository).save(outbox);
        }
    }

    @Nested
    @DisplayName("retryFailedEvents")
    class RetryFailedEvents {
        @Test
        void retries_failed_events() {
            OutboxEvent failed = mock(OutboxEvent.class);
            when(outboxRepository.findByStatusAndRetryCountLessThanOrderByOccurredAtAsc(
                eq(OutboxEvent.OutboxStatus.FAILED), anyInt(), anyInt()
            )).thenReturn(List.of(failed));

            IntegrationEvent event = mock(IntegrationEvent.class);
            when(serializer.deserialize(failed)).thenReturn(event);

            scheduler.retryFailedEvents();

            verify(eventProducer).send(event);
            verify(failed).markAsPublished();
            verify(outboxRepository).save(failed);
        }
    }

    @Nested
    @DisplayName("processDlqEvents")
    class ProcessDlqEvents {
        @Test
        void sends_dlq_candidates_and_marks_dlq() {
            OutboxEvent candidate = mock(OutboxEvent.class);
            when(candidate.getId()).thenReturn(42L);
            when(candidate.getPayload()).thenReturn("{json}");
            when(candidate.getEventType()).thenReturn("enrollment.created");

            when(outboxRepository.findDlqCandidates(anyInt(), anyInt()))
                .thenReturn(List.of(candidate));

            scheduler.processDlqEvents();

            verify(eventProducer).sendToDlq("{json}", "enrollment.created");
            ArgumentCaptor<java.time.LocalDateTime> timeCaptor = ArgumentCaptor.forClass(java.time.LocalDateTime.class);
            verify(outboxRepository).markAsDlq(eq(42L), timeCaptor.capture());
            assertThat(timeCaptor.getValue()).isNotNull();
        }

        @Test
        void when_no_candidates_nothing_is_sent_or_marked() {
            when(outboxRepository.findDlqCandidates(anyInt(), anyInt()))
                .thenReturn(List.of());

            scheduler.processDlqEvents();

            // No interactions with producer or repository markAsDlq
            verify(outboxRepository).findDlqCandidates(anyInt(), anyInt());
        }

        @Test
        void when_send_to_dlq_fails_it_does_not_mark_as_dlq() {
            OutboxEvent candidate = mock(OutboxEvent.class);
            when(candidate.getId()).thenReturn(100L);
            when(candidate.getPayload()).thenReturn("{json}");
            when(candidate.getEventType()).thenReturn("enrollment.created");

            when(outboxRepository.findDlqCandidates(anyInt(), anyInt()))
                .thenReturn(List.of(candidate));

            doThrow(new RuntimeException("broker down")).when(eventProducer)
                .sendToDlq("{json}", "enrollment.created");

            scheduler.processDlqEvents();

            // markAsDlq should not be called when sending to DLQ fails
            verify(outboxRepository).findDlqCandidates(anyInt(), anyInt());
            org.mockito.Mockito.verify(outboxRepository, org.mockito.Mockito.never())
                .markAsDlq(org.mockito.ArgumentMatchers.anyLong(), org.mockito.ArgumentMatchers.any(java.time.LocalDateTime.class));
        }

        @Test
        void fails_three_times_then_goes_to_dlq() {
            // Arrange a single outbox event that will always fail to publish
            OutboxEvent outbox = mock(OutboxEvent.class);
            when(outbox.getId()).thenReturn(7L);
            when(outbox.getPayload()).thenReturn("{json}");
            when(outbox.getEventType()).thenReturn("enrollment.created");

            IntegrationEvent event = mock(IntegrationEvent.class);
            when(serializer.deserialize(outbox)).thenReturn(event);
            // Producer always fails
            doThrow(new RuntimeException("send failed")).when(eventProducer).send(org.mockito.ArgumentMatchers.any(IntegrationEvent.class));

            // First polling picks up PENDING once, then no more
            when(outboxRepository.findByStatusAndRetryCountLessThanOrderByOccurredAtAsc(
                eq(OutboxEvent.OutboxStatus.PENDING), anyInt(), anyInt()
            )).thenReturn(List.of(outbox)).thenReturn(List.of());

            // Two retries pick up FAILED twice (< MAX_RETRY_COUNT = 3), then stop
            when(outboxRepository.findByStatusAndRetryCountLessThanOrderByOccurredAtAsc(
                eq(OutboxEvent.OutboxStatus.FAILED), anyInt(), anyInt()
            )).thenReturn(List.of(outbox)).thenReturn(List.of(outbox)).thenReturn(List.of());

            // After reaching threshold, it becomes DLQ candidate
            when(outboxRepository.findDlqCandidates(anyInt(), anyInt()))
                .thenReturn(List.of(outbox));

            // Act: 1 failure on publish + 2 retry failures -> then DLQ
            scheduler.pollAndPublish();
            scheduler.retryFailedEvents();
            scheduler.retryFailedEvents();
            scheduler.processDlqEvents();

            // Assert: send tried 3 times and failed
            org.mockito.Mockito.verify(eventProducer, org.mockito.Mockito.times(3))
                .send(org.mockito.ArgumentMatchers.any(IntegrationEvent.class));
            // markAsFailed called for each failure
            org.mockito.Mockito.verify(outbox, org.mockito.Mockito.times(3))
                .markAsFailed(org.mockito.ArgumentMatchers.contains("send failed"));

            // Then sent to DLQ and marked
            verify(eventProducer).sendToDlq("{json}", "enrollment.created");
            verify(outboxRepository).markAsDlq(eq(7L), org.mockito.ArgumentMatchers.any(java.time.LocalDateTime.class));
        }
    }
}
