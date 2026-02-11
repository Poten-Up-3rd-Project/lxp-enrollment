package com.lxp.enrollment.infra.required.messaging.producer;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.infra.required.messaging.exception.EventPublishException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnrollmentIntegrationEventProducerTest {

    RabbitTemplate rabbitTemplate;
    EventSerializer serializer;
    EnrollmentIntegrationEventProducer producer;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        serializer = mock(EventSerializer.class);
        producer = new EnrollmentIntegrationEventProducer(rabbitTemplate, serializer);
    }

    @Test
    void sends_with_exchange_and_eventType_as_routing_key() {
        RabbitTemplate rabbit = mock(RabbitTemplate.class);
        EventSerializer serializer = mock(EventSerializer.class);
        EnrollmentIntegrationEventProducer producer = new EnrollmentIntegrationEventProducer(rabbit, serializer);

        IntegrationEvent event = mock(IntegrationEvent.class);
        when(event.getEventType()).thenReturn("enroll.created");
        when(serializer.serialize(event)).thenReturn("{json}");

        producer.send(event);

        verify(rabbit).convertAndSend("enroll.events", "enroll.created", "{json}");
    }

    @Test
    void wraps_broker_exception_into_EventPublishException() {
        RabbitTemplate rabbit = mock(RabbitTemplate.class);
        EventSerializer serializer = mock(EventSerializer.class);
        EnrollmentIntegrationEventProducer producer = new EnrollmentIntegrationEventProducer(rabbit, serializer);

        IntegrationEvent event = mock(IntegrationEvent.class);
        when(event.getEventType()).thenReturn("enroll.created");
        when(event.getEventId()).thenReturn("evt-1");
        when(serializer.serialize(event)).thenReturn("{json}");

        doThrow(new RuntimeException("broker down")).when(rabbit)
            .convertAndSend("enroll.events", "enroll.created", "{json}");

        assertThatThrownBy(() -> producer.send(event))
            .isInstanceOf(EventPublishException.class)
            .hasMessageContaining("evt-1");
    }

    @Test
    void send_uses_exchange_and_routing_key() {
        IntegrationEvent event = mock(IntegrationEvent.class);
        when(event.getEventType()).thenReturn("enroll.created");
        when(serializer.serialize(event)).thenReturn("{json}");

        producer.send(event);

        verify(serializer).serialize(event);
        verify(rabbitTemplate).convertAndSend("enroll.events", "enroll.created", "{json}");
    }

    @Test
    void sendToDlq_uses_dlq_exchange() {
        producer.sendToDlq("{json}", "enroll.deleted");

        verify(rabbitTemplate).convertAndSend("enroll.dlq", "enroll.deleted", "{json}");
    }
}
