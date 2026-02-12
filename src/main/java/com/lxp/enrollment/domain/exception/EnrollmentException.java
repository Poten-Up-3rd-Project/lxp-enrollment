package com.lxp.enrollment.domain.exception;

import com.lxp.common.domain.exception.DomainException;
import com.lxp.common.domain.exception.ErrorCode;

public class EnrollmentException extends DomainException {
    public EnrollmentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EnrollmentException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public EnrollmentException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
