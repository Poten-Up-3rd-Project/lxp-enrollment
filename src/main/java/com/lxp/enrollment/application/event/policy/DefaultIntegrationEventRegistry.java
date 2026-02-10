package com.lxp.enrollment.application.event.policy;

import com.lxp.enrollment.application.required.EventProducer;
import com.lxp.enrollment.application.required.OutboxEventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultIntegrationEventRegistry implements IntegrationEventRegistry {

    private final OutboxEventStore outboxStore;
    private final EventProducer eventProducer;

    @Override
    public void register(IntegrationEventPublishCommand command) {
        switch (command.policy()) {
            case OUTBOX_REQUIRED -> outboxStore.save(command.event(), command.metadata());
            case FIRE_AND_FORGET -> eventProducer.send(command.event());
        }
    }
}
