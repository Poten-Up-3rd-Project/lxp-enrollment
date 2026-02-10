package com.lxp.enrollment.infra.required.messaging;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.infra.required.messaging.producer.EnrollmentIntegrationEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

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
    void send_uses_exchange_and_routing_key() {
        IntegrationEvent event = mock(IntegrationEvent.class);
        when(event.getEventType()).thenReturn("enrollment.created");
        when(serializer.serialize(event)).thenReturn("{json}");

        producer.send(event);

        verify(serializer).serialize(event);
        verify(rabbitTemplate).convertAndSend("enrollment.exchange", "enrollment.created", "{json}");
    }

    @Test
    void sendToDlq_uses_dlq_exchange() {
        producer.sendToDlq("{json}", "enrollment.cancelled");

        verify(rabbitTemplate).convertAndSend("dlq.exchange", "enrollment.cancelled", "{json}");
    }
}
