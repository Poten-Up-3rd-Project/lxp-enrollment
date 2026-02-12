package com.lxp.enrollment.infra.required.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.common.infrastructure.persistence.OutboxOptions;
import com.lxp.enrollment.application.event.integration.EventMetadata;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.application.required.OutboxEventStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxEventStoreAdapter implements OutboxEventStore {

    private final EnrollmentOutboxRepository outboxRepository;
    private final EventSerializer serializer;

    @Override
    public void save(IntegrationEvent event, EventMetadata metadata, OutboxOptions options) {
        if (outboxRepository.findByEventId(event.getEventId()).isPresent()) {
            log.debug("Outbox already has eventId={}, skipping save", event.getEventId());
            return;
        }

        OutboxEvent outbox = new OutboxEvent(
            event.getEventId(),
            event.getEventType(),
            metadata.aggregateEventType(),
            metadata.aggregateId(),
            serializer.serialize(event),
            event.getOccurredAt(),
            options
        );
        try {
            outboxRepository.save(outbox);
        } catch (DataIntegrityViolationException e) {
            log.debug("Outbox insert raced for eventId={}, treating as idempotent save", event.getEventId());
        }
    }
}
