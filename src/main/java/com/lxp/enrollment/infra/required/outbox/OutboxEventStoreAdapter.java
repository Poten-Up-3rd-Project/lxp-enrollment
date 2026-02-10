package com.lxp.enrollment.infra.required.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.enrollment.application.event.integration.EventMetadata;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.application.required.OutboxEventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventStoreAdapter implements OutboxEventStore {

    private final EnrollmentOutboxRepository outboxRepository;
    private final EventSerializer serializer;

    @Override
    public void save(IntegrationEvent event, EventMetadata metadata) {
        OutboxEvent outbox = new OutboxEvent(
            event.getEventId(),
            event.getEventType(),
            metadata.aggregateEventType(),
            metadata.aggregateId(),
            serializer.serialize(event),
            event.getOccurredAt()
        );
        outboxRepository.save(outbox);
    }
}
