package com.lxp.enrollment.application.required;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.infrastructure.persistence.OutboxEvent;

public interface EventSerializer {

    String serialize(IntegrationEvent event);

    IntegrationEvent deserialize(OutboxEvent outbox);
}
