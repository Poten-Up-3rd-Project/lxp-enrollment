package com.lxp.enrollment.application.event.integration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lxp.common.application.event.BaseIntegrationEventEnvelope;
import com.lxp.enrollment.application.event.integration.payload.EnrollmentEventPayload;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollmentCancelledIntegrationEvent extends BaseIntegrationEventEnvelope<EnrollmentEventPayload> {

    private static final String SOURCE = "lxp.enrollment.service";

    @JsonCreator
    public EnrollmentCancelledIntegrationEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("occurredAt") LocalDateTime occurredAt,
        @JsonProperty("correlationId") String correlationId,
        @JsonProperty("causationId") String causationId,
        @JsonProperty("payload") EnrollmentEventPayload payload) {
        super(eventId, occurredAt, SOURCE, correlationId, causationId, payload);
    }

    @Override
    public String getEventType() {
        return "enrollment.cancelled";
    }
}
