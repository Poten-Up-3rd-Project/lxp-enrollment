package com.lxp.enrollment.infra.required.web.exception;

public class ApiException extends RuntimeException {

    private String code;
    private String additionalInfo;

    public ApiException(String code, String message, String additionalInfo) {
        super(message);
        this.code = code;
    }

    public ApiException(String code, String message, String additionalInfo, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }
}
