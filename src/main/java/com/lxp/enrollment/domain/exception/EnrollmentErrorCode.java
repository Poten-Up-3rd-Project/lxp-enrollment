package com.lxp.enrollment.domain.exception;

import com.lxp.common.domain.exception.ErrorCode;

public enum EnrollmentErrorCode implements ErrorCode {

    USER_ID_IS_NULL("ENROLLMENT_0001", "userId 가 null 입니다.", "BAD_REQUEST"),
    COURSE_ID_IS_NULL("ENROLLMENT_0002", "courseId 가 null 입니다.", "BAD_REQUEST"),
    INVALID_STATUS_CHANGE_INTO_IN_PROGRESS(
            "ENROLLMENT_0003",
            "허용되지 않은 수강 상태 변경: IN_PROGRESS 상태로의 변경은 오직 ENROLLED 상태에서만 가능합니다.",
            "BAD_REQUEST"),
    INVALID_STATUS_CHANGE_INTO_CANCELLED(
            "ENROLLMENT_0003",
            "허용되지 않은 수강 상태 변경: CANCELLED 상태로의 변경은 오직 ENROLLED, IN_PROGRESS 상태에서만 가능합니다.",
            "BAD_REQUEST"),
    INVALID_STATUS_CHANGE_INTO_COMPLETED(
            "ENROLLMENT_0003",
            "허용되지 않은 수강 상태 변경: COMPLETED 상태로의 변경은 오직 IN_PROGRESS 상태에서만 가능합니다.",
            "BAD_REQUEST"),
    INVALID_STATUS_CHANGE_INTO_ENROLLED(
            "ENROLLMENT_0003",
            "허용되지 않은 수강 상태 변경: ENROLLED 상태로의 변경은 오직 CANCELLED 상태에서만 가능합니다.",
            "BAD_REQUEST"),
    ;

    private final String code;
    private final String message;
    private final String group;

    EnrollmentErrorCode(String code, String message, String group) {
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
