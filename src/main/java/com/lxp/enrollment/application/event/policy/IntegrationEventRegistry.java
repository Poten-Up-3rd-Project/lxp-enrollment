package com.lxp.enrollment.application.event.policy;

public interface IntegrationEventRegistry {
    void register(IntegrationEventPublishCommand command);
}
