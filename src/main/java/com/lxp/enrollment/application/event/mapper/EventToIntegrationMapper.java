package com.lxp.enrollment.application.event.mapper;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.enrollment.application.event.integration.EnrollmentCreatedIntegrationEvent;
import com.lxp.enrollment.application.event.integration.payload.EnrollmentEventPayload;
import com.lxp.enrollment.application.required.DomainEventToIntegrationEventConverter;
import com.lxp.enrollment.domain.event.EnrollmentCreated;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EventToIntegrationMapper implements DomainEventToIntegrationEventConverter {

    @Override
    public IntegrationEvent convert(BaseDomainEvent domainEvent) {
        if (domainEvent instanceof EnrollmentCreated created) {
            return toEnrollmentCreatedIntegrationEvent(created);
        } else {
            throw new IllegalArgumentException("Unsupported event type: " + domainEvent.getClass().getSimpleName());
        }
    }

    @Override
    public List<IntegrationEvent> toIntegrationEvents(BaseDomainEvent event) {
        return List.of(convert(event));
    }

    private EnrollmentCreatedIntegrationEvent toEnrollmentCreatedIntegrationEvent(EnrollmentCreated created) {
        return new EnrollmentCreatedIntegrationEvent(
            UUID.randomUUID().toString(),
            created.getOccurredAt(),
            created.getEventId(),
            null,
            new EnrollmentEventPayload(
                created.getCourseUUID(),
                created.getUserUUID()
            )
        );
    }
}
