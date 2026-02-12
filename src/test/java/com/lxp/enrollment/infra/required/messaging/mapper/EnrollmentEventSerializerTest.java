package com.lxp.enrollment.infra.required.messaging.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;
import com.lxp.enrollment.application.event.integration.EnrollmentCreatedIntegrationEvent;
import com.lxp.enrollment.application.event.integration.payload.EnrollmentEventPayload;
import com.lxp.enrollment.infra.required.messaging.messaging.EventSerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnrollmentEventSerializerTest {

    @Test
    void serialize_and_deserialize_roundtrip_for_created() {
        ObjectMapper om = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        EnrollmentEventSerializer ser = new EnrollmentEventSerializer(om);

        IntegrationEvent evt = new EnrollmentCreatedIntegrationEvent(
            "evt-1",
            LocalDateTime.of(2026, 2, 11, 0, 0),
            "corr-1",
            null,
            new EnrollmentEventPayload("course-1", "user-1")
        );

        String json = ser.serialize(evt);
        OutboxEvent outbox = mockOutbox("enroll.created", json);

        IntegrationEvent back = ser.deserialize(outbox);
        assertThat(back.getEventId()).isEqualTo("evt-1");
        assertThat(back.getEventType()).isEqualTo("enroll.created");
    }

    @Test
    void unknown_event_type_throws() {
        ObjectMapper om = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        EnrollmentEventSerializer ser = new EnrollmentEventSerializer(om);

        OutboxEvent outbox = mockOutbox("unknown.type", "{}");
        assertThatThrownBy(() -> ser.deserialize(outbox))
            .isInstanceOf(EventSerializationException.class)
            .hasMessageContaining("Deserialization failed");
    }

    private OutboxEvent mockOutbox(String type, String payload) {
        return new OutboxEvent(
            "evt-x",
            type,
            "EnrollmentCreated",
            "agg-1",
            payload,
            LocalDateTime.now(),
            null
        );
    }
}
