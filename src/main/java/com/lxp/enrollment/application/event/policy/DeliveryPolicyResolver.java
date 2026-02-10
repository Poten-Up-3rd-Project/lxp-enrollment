package com.lxp.enrollment.application.event.policy;

import com.lxp.common.domain.event.BaseDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPolicyResolver {

    public DeliveryPolicy resolve(BaseDomainEvent event) {
        String eventType = event.getClass().getSimpleName();

        return switch (eventType) {
            case "EnrollmentCreated", "EnrollmentCancelled" -> DeliveryPolicy.OUTBOX_REQUIRED;
            default -> DeliveryPolicy.FIRE_AND_FORGET;
        };
    }
}
