package com.lxp.common.passport;

public class InvalidPassportException extends RuntimeException {

    public InvalidPassportException(String message) {
        super(message);
    }

    public InvalidPassportException(String message, Throwable cause) {
        super(message, cause);
    }
}
