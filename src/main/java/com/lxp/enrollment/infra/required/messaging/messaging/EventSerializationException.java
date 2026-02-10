package com.lxp.enrollment.infra.required.messaging.messaging;

public class EventSerializationException extends RuntimeException {
    public EventSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
