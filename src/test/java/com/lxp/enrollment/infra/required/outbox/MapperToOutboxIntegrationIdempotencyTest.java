package com.lxp.enrollment.infra.required.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxOptions;
import com.lxp.enrollment.application.event.integration.EventMetadata;
import com.lxp.enrollment.application.event.mapper.EventToIntegrationMapper;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.domain.event.EnrollmentCreated;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MapperToOutboxIntegrationIdempotencyTest {

    @Test
    void mapping_same_domain_event_twice_and_saving_results_in_single_persist() {
        // Arrange
        EventToIntegrationMapper mapper = new EventToIntegrationMapper();
        EnrollmentOutboxRepository repo = mock(EnrollmentOutboxRepository.class);
        EventSerializer serializer = mock(EventSerializer.class);
        OutboxEventStoreAdapter adapter = new OutboxEventStoreAdapter(repo, serializer);

        // Domain event (same occurrence)
        String aggregateId = "enr-123";
        LocalDateTime occurredAt = LocalDateTime.of(2026, 2, 11, 12, 0, 0);
        EnrollmentCreated domain1 = new EnrollmentCreated("evt-x", occurredAt, aggregateId, "course-1", "user-1");
        EnrollmentCreated domain1Dup = new EnrollmentCreated("evt-x", occurredAt, aggregateId, "course-1", "user-1");

        // Map to integration events (should produce the same eventId deterministically)
        IntegrationEvent i1 = mapper.convert(domain1);
        IntegrationEvent i2 = mapper.convert(domain1Dup);

        // Repo existence check: first call empty -> save; second call present -> skip
        when(repo.findByEventId(i1.getEventId())).thenReturn(Optional.empty()).thenReturn(Optional.of(mock(com.lxp.common.infrastructure.persistence.OutboxEvent.class)));
        when(serializer.serialize(any())).thenReturn("{json}");

        OutboxOptions opts = new OutboxOptions(50, null, null, 3, Duration.ofSeconds(5), true, false);
        EventMetadata md = new EventMetadata(aggregateId, "EnrollmentCreated", "enrollment.exchange");

        // Act: save twice with logically same event
        adapter.save(i1, md, opts);
        adapter.save(i2, md, opts);

        // Assert: repo.save called only once
        verify(repo, times(1)).save(any());
    }
}
