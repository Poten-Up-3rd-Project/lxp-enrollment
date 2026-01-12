package com.lxp.enrollment.domain.exception;

import com.lxp.common.domain.exception.ErrorCode;

public enum EnrollmentErrorCode implements ErrorCode {

    USER_ID_IS_NULL("ENROLLMENT_0001", "userId 가 null 입니다.", "BAD_REQUEST"),
    COURSE_ID_IS_NULL("ENROLLMENT_0002", "courseId 가 null 입니다.", "BAD_REQUEST"),
    INVALID_STATUS_CHANGE_INTO_IN_PROGRESS(
            "ENROLLMENT_0003",
            "허용되지 않은 수강 상태 변경: IN_PROGRESS 상태로의 변경은 오직 ENROLLED 상태에서만 가능합니다.",
            "CONFLICT"),
    INVALID_STATUS_CHANGE_INTO_CANCELLED(
            "ENROLLMENT_0004",
            "허용되지 않은 수강 상태 변경: CANCELLED 상태로의 변경은 오직 ENROLLED, IN_PROGRESS 상태에서만 가능합니다.",
            "CONFLICT"),
    INVALID_STATUS_CHANGE_INTO_COMPLETED(
            "ENROLLMENT_0005",
            "허용되지 않은 수강 상태 변경: COMPLETED 상태로의 변경은 오직 IN_PROGRESS 상태에서만 가능합니다.",
            "CONFLICT"),
    INVALID_STATUS_CHANGE_INTO_ENROLLED(
            "ENROLLMENT_0006",
            "허용되지 않은 수강 상태 변경: ENROLLED 상태로의 변경은 오직 CANCELLED 상태에서만 가능합니다.",
            "CONFLICT"),
    INVALID_DELETION_ATTEMPT_FROM_ENROLLED_OR_IN_PROGRESS_STATUS(
            "ENROLLMENT_0007",
            "ENROLLED 또는 IN_PROGRESS 상태일때는 수강 기록을 삭제할 수 없습니다.",
            "CONFLICT"
    ),
    INVALID_CHRONOLOGY(
            "ENROLLMENT_0008",
            "시점 정합성이 깨졌습니다.",
            "CONFLICT"
    ),
    ENROLLMENT_NOT_FOUND(
            "ENROLLMENT_0009",
            "수강 정보가 없습니다.",
            "NOT_FOUND"
    ),
    INVALID_USER_ID(
            "ENROLLMENT_0010",
            "유효하지 않은 UserId 형식. UUID 일 것으로 기대했으나, 다른 형식으로 전달되었습니다.",
            "BAD_REQUEST"
    ),
    INVALID_COURSE_ID(
            "ENROLLMENT_0011",
            "유효하지 않은 CourseId 형식. UUID 일 것으로 기대했으나, 다른 형식으로 전달되었습니다.",
            "BAD_REQUEST"
    ),
    INVALID_ENROLLMENT_ID(
            "ENROLLMENT_0012",
            "유효하지 않은 EnrollmentId 형식. UUID 일 것으로 기대했으나, 다른 형식으로 전달되었습니다.",
            "BAD_REQUEST"
    ),
    COURSE_NOT_FOUND(
            "ENROLLMENT_0013",
            "대상 강좌를 찾을 수 없습니다.",
            "NOT_FOUND"
    ),
    CANCELLED_AT_IS_NULL(
            "ENROLLMENT_0014",
            "cancelledAt 이 null 입니다.",
            "BAD_REQUEST"
    ),
    CANCEL_TYPE_IS_NULL(
            "ENROLLMENT_0015",
            "cancelType 이 null 입니다.",
            "BAD_REQUEST"
    ),
    CANCEL_REASON_TYPE_IS_NULL(
            "ENROLLMENT_0016",
            "cancelReasonType 이 null 입니다.",
            "BAD_REQUEST"
    ),

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
