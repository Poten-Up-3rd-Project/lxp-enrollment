package com.lxp.enrollment.application.required;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.enrollment.application.event.integration.EventMetadata;

@FunctionalInterface
public interface OutboxEventStore {

    void save(IntegrationEvent event, EventMetadata metadata);
}
