package com.lxp.enrollment.application.event.policy;


import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.application.event.policy.EventPublishPolicy;
import com.lxp.enrollment.application.event.integration.EventMetadata;

public record IntegrationEventPublishCommand(
    IntegrationEvent event,
    EventPublishPolicy policy,
    EventMetadata metadata
) {
    public static IntegrationEventPublishCommand of(
        IntegrationEvent event,
        EventPublishPolicy policy,
        EventMetadata metadata
    ) {
        return new IntegrationEventPublishCommand(event, policy, metadata);
    }


    public static IntegrationEventPublishCommand withoutMetadata(
        IntegrationEvent event,
        EventPublishPolicy policy
    ) {
        return new IntegrationEventPublishCommand(event, policy, null);
    }
}

