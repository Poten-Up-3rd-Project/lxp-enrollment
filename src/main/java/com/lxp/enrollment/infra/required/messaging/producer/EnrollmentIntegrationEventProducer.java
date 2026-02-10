package com.lxp.enrollment.infra.required.messaging.producer;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.enrollment.application.required.EventProducer;
import com.lxp.enrollment.application.required.EventSerializer;
import com.lxp.enrollment.infra.required.messaging.exception.EventPublishException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentIntegrationEventProducer implements EventProducer {

    private static final String ENROLLMENT_EXCHANGE = "enrollment.exchange";
    private static final String DLQ_EXCHANGE = "dlq.exchange";

    private final RabbitTemplate rabbitTemplate;
    private final EventSerializer serializer;

    @Override
    public void send(IntegrationEvent event) {
        String json = serializer.serialize(event);
        try {
            rabbitTemplate.convertAndSend(ENROLLMENT_EXCHANGE, event.getEventType(), json);
        } catch (Exception e) {
            throw new EventPublishException("Failed to send event: " + event.getEventId(), e);
        }
    }

    @Override
    public void sendToDlq(String payload, String eventType) {
        try {
            rabbitTemplate.convertAndSend(DLQ_EXCHANGE, eventType, payload);
        } catch (Exception e) {
            throw new EventPublishException("Failed to send event to DLQ: " + eventType, e);
        }
    }


}
