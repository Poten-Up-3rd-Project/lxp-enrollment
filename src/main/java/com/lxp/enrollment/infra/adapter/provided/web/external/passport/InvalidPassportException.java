package com.lxp.enrollment.infra.adapter.provided.web.external.passport;

public class InvalidPassportException extends RuntimeException {

    public InvalidPassportException(String message) {
        super(message);
    }
}
