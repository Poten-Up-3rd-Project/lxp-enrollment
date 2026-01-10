package com.lxp.common.exception;

import com.lxp.common.domain.exception.ErrorCode;

public enum CommonErrorCode implements ErrorCode {

    UNSUPPORTED_HTTP_METHOD("ENROLLMENT_C_0001", "지원되지 않는 HTTP 메서드", "BAD_REQUEST"),
    MISSING_HTTP_HEADER("ENROLLMENT_C_0002", "필수 헤더 누락", "BAD_REQUEST"),
    MISSING_REQUEST_PARAMETER("ENROLLMENT_C_0003", "필수 파라미터 누락", "BAD_REQUEST"),
    VALIDATION_FAILED("ENROLLMENT_C_0004", "파라미터 검증 실패", "BAD_REQUEST"),
    SERVLET_EXCEPTION("ENROLLMENT_C_0005", "예기치 못한 서블릿 예외", "INTERNAL_SERVER_ERROR"),
    DATA_ACCESS_FAILED("ENROLLMENT_C_0006", "예기치 못한 데이터 접근 실패", "INTERNAL_SERVER_ERROR"),
    UNEXPECTED_SERVER_ERROR("ENROLLMENT_C_0007", "예기치 못한 서버 예외", "INTERNAL_SERVER_ERROR"),
    ;

    private final String code;
    private final String message;
    private final String group;

    CommonErrorCode(String code, String message, String group) {
        this.code = code;
        this.message = message;
        this.group = group;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getGroup() {
        return this.group;
    }
}
