package com.lxp.enrollment.infra.required.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxOptions;
import com.lxp.enrollment.application.event.integration.EventMetadata;
import com.lxp.enrollment.application.required.EventSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Duration;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutboxEventStoreAdapterIdempotencyTest {

    @Test
    void skips_save_when_eventId_already_exists() {
        EnrollmentOutboxRepository repo = mock(EnrollmentOutboxRepository.class);
        EventSerializer ser = mock(EventSerializer.class);
        OutboxEventStoreAdapter adapter = new OutboxEventStoreAdapter(repo, ser);

        IntegrationEvent evt = mock(IntegrationEvent.class);
        when(evt.getEventId()).thenReturn("evt-1");
        when(repo.findByEventId("evt-1")).thenReturn(Optional.of(mock(com.lxp.common.infrastructure.persistence.OutboxEvent.class)));

        adapter.save(evt, new EventMetadata("agg-1", "EnrollmentCreated", "enrollment.exchange"), defaultOpts());

        verify(repo, never()).save(any());
    }

    @Test
    void treats_unique_violation_as_idempotent_success() {
        EnrollmentOutboxRepository repo = mock(EnrollmentOutboxRepository.class);
        EventSerializer ser = mock(EventSerializer.class);
        OutboxEventStoreAdapter adapter = new OutboxEventStoreAdapter(repo, ser);

        IntegrationEvent evt = mock(IntegrationEvent.class);
        when(evt.getEventId()).thenReturn("evt-2");
        when(evt.getEventType()).thenReturn("enrollment.created");
        when(ser.serialize(evt)).thenReturn("{json}");

        // exists check says "not present", but save collides
        when(repo.findByEventId("evt-2")).thenReturn(Optional.empty());
        doThrow(new DataIntegrityViolationException("duplicate key")).when(repo).save(any());

        adapter.save(evt, new EventMetadata("agg-1", "EnrollmentCreated", "enrollment.exchange"), defaultOpts());

        // No exception should escape
    }

    @Test
    void saves_once_when_called_twice_with_same_eventId() {
        EnrollmentOutboxRepository repo = mock(EnrollmentOutboxRepository.class);
        EventSerializer ser = mock(EventSerializer.class);
        OutboxEventStoreAdapter adapter = new OutboxEventStoreAdapter(repo, ser);

        IntegrationEvent evt = mock(IntegrationEvent.class);
        when(evt.getEventId()).thenReturn("evt-dup");
        when(evt.getEventType()).thenReturn("enrollment.created");
        when(ser.serialize(evt)).thenReturn("{json}");

        // First call: not present -> save; Second call: present -> skip
        when(repo.findByEventId("evt-dup")).thenReturn(Optional.empty()).thenReturn(Optional.of(mock(com.lxp.common.infrastructure.persistence.OutboxEvent.class)));

        adapter.save(evt, new EventMetadata("agg-1", "EnrollmentCreated", "enrollment.exchange"), defaultOpts());
        adapter.save(evt, new EventMetadata("agg-1", "EnrollmentCreated", "enrollment.exchange"), defaultOpts());

        verify(repo, times(1)).save(any());
    }

    private OutboxOptions defaultOpts() {
        return new OutboxOptions(50, null, null, 3, Duration.ofSeconds(5), true, false);
    }
}
