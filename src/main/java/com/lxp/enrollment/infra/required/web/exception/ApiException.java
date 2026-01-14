package com.lxp.enrollment.infra.required.web.exception;

import com.lxp.common.domain.exception.ErrorCode;

public class ApiException extends RuntimeException {

    private ErrorCode errorCode;
    private String additionalInfo;

    public ApiException(ErrorCode errorCode, String additionalInfo) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.additionalInfo = additionalInfo;
    }

    public ApiException(ErrorCode errorCode, String additionalInfo, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.additionalInfo = additionalInfo;
    }

    public String getCode() {
        return this.errorCode.getCode();
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }
}
