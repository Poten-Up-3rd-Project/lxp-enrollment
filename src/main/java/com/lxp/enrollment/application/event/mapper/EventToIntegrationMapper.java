package com.lxp.enrollment.application.event.mapper;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.enrollment.application.event.integration.EnrollmentCreatedIntegrationEvent;
import com.lxp.enrollment.application.event.integration.payload.EnrollmentEventPayload;
import com.lxp.enrollment.application.required.DomainEventToIntegrationEventConverter;
import com.lxp.enrollment.domain.event.EnrollmentCreated;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
public class EventToIntegrationMapper implements DomainEventToIntegrationEventConverter {

    private static final DateTimeFormatter TS = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
        // Deterministic integration eventId to ensure producer-side idempotency without colliding across CRUD types
        String name = created.getAggregateId() + "|created|" + TS.format(created.getOccurredAt());
        String deterministicId = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8)).toString();

        return new EnrollmentCreatedIntegrationEvent(
            deterministicId,                     // integration eventId (idempotency key)
            created.getOccurredAt(),
            created.getAggregateId(),            // correlationId: aggregate (enrollmentUuid)
            null,                                // causationId (optional)
            new EnrollmentEventPayload(
                created.getCourseUUID(),
                created.getUserUUID()
            )
        );
    }
}
