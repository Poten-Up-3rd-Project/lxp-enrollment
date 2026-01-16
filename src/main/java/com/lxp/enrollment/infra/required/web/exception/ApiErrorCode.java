package com.lxp.enrollment.infra.required.web.exception;

import com.lxp.common.domain.exception.ErrorCode;

public enum ApiErrorCode implements ErrorCode {
    INTERNAL_API_CALL_FAILS(
            "API_CALL_FAILS",
            "내부 API 요청이 실패했습니다.",
            "INTERNAL_SERVER_ERROR"
    ),
    INTERNAL_API_CALL_ERROR_RESPONSE(
            "INTERNAL_API_CALL_ERROR",
            "내부 API 에서 오류가 응답되었습니다.",
            "INTERNAL_SERVER_ERROR"
    ),
    EMPTY_RESPONSE_BODY(
            "EMPTY_RESPONSE_BODY",
            "응답 본문이 필요하나, 빈 채로 응답되었습니다.",
            "INTERNAL_SERVER_ERROR"
    ),
    REQUIRED_FIELD_IS_NOT_PROVIDED(
            "REQUIRED_FIELD_IS_NOT_PROVIDED",
            "응답 본문 내에 필수 값이 없습니다.",
            "INTERNAL_SERVER_ERROR"
    )
    ;

    private final String code;
    private final String message;
    private final String group;

    ApiErrorCode(String code, String message, String group) {
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
