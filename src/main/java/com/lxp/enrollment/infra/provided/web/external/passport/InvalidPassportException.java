package com.lxp.enrollment.infra.provided.web.external.passport;

public class InvalidPassportException extends RuntimeException {

    public InvalidPassportException(String message) {
        super(message);
    }
}
