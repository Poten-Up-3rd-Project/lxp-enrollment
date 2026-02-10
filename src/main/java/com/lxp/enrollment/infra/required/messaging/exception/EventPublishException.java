package com.lxp.enrollment.infra.required.messaging.exception;

public class EventPublishException extends RuntimeException {

    public EventPublishException(String message, Throwable cause) {
        super(message, cause);
    }

}
