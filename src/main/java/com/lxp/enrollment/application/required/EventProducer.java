package com.lxp.enrollment.application.required;

import com.lxp.common.application.event.IntegrationEvent;

public interface EventProducer {

    void send(IntegrationEvent event);

    void sendToDlq(String payload, String eventType);
}
