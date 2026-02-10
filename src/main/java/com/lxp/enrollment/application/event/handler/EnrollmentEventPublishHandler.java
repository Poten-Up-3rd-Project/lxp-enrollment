package com.lxp.enrollment.application.event.handler;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.common.event.CrudEvent;
import com.lxp.enrollment.application.event.integration.EventMetadata;
import com.lxp.enrollment.application.event.policy.DeliveryPolicy;
import com.lxp.enrollment.application.event.policy.DeliveryPolicyResolver;
import com.lxp.enrollment.application.event.policy.IntegrationEventPublishCommand;
import com.lxp.enrollment.application.event.policy.IntegrationEventRegistry;
import com.lxp.enrollment.application.required.DomainEventToIntegrationEventConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentEventPublishHandler {

    private final IntegrationEventRegistry registry;
    private final DomainEventToIntegrationEventConverter mapper;
    private final DeliveryPolicyResolver policyResolver;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBeforeCommit(CrudEvent event) {
        BaseDomainEvent domainEvent = (BaseDomainEvent) event;
        DeliveryPolicy policy = policyResolver.resolve(domainEvent);

        if (policy == DeliveryPolicy.OUTBOX_REQUIRED) {
            List<IntegrationEvent> integrationEvents = mapper.toIntegrationEvents(domainEvent);
            for (IntegrationEvent integrationEvent : integrationEvents) {
                registry.register(IntegrationEventPublishCommand.outbox(
                    integrationEvent,
                    EventMetadata.from(domainEvent, "enrollment.events")
                ));
            }
        }

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(CrudEvent event) {
        BaseDomainEvent domainEvent = (BaseDomainEvent) event;

    }
}
