package com.lxp.enrollment.infra.required.messaging.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.enrollment.application.event.integration.EnrollmentCancelledIntegrationEvent;
import com.lxp.enrollment.application.event.integration.EnrollmentCreatedIntegrationEvent;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.infra.required.messaging.messaging.EventSerializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentEventSerializer implements EventSerializer {

    private final ObjectMapper objectMapper;

    @Override
    public String serialize(IntegrationEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new EventSerializationException("Serialization failed", e);
        }
    }

    @Override
    public IntegrationEvent deserialize(OutboxEvent outbox) {
        try {
            Class<? extends IntegrationEvent> eventClass = resolveEventClass(outbox.getEventType());
            return objectMapper.readValue(outbox.getPayload(), eventClass);
        } catch (Exception e) {
            throw new EventSerializationException("Deserialization failed", e);
        }
    }

    Class<? extends IntegrationEvent> resolveEventClass(String eventType) {
        return switch (eventType) {
            case "enrollment.created" -> EnrollmentCreatedIntegrationEvent.class;
            case "enrollment.cancelled" -> EnrollmentCancelledIntegrationEvent.class;
            default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
        };
    }
}
