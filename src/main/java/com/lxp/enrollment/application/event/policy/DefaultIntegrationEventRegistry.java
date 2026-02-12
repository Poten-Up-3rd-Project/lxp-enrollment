package com.lxp.enrollment.application.event.policy;

import com.lxp.common.application.event.policy.EventPublishPolicy;
import com.lxp.common.infrastructure.persistence.OutboxOptions;
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
        EventPublishPolicy policy = command.policy();

        if (!policy.delivery().requiresOutbox()) {
            eventProducer.send(command.event());
            return;
        }

        outboxStore.save(
            command.event(),
            command.metadata(),
            OutboxOptions.from(policy, command.event())
        );
    }
}
