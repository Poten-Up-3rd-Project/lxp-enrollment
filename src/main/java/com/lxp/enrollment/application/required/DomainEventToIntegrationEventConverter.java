package com.lxp.enrollment.application.required;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.domain.event.BaseDomainEvent;

import java.util.List;

public interface DomainEventToIntegrationEventConverter {

    IntegrationEvent convert(BaseDomainEvent domainEvent);

    List<IntegrationEvent> toIntegrationEvents(BaseDomainEvent event);
}

