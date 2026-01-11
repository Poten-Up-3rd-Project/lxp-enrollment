package com.lxp.enrollment.infra.adapter.provided.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.port.provided.dto.CancelCourseResult;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CancelCourseResponse(
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime enrolledAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime activatedAt,
        CancelDetailsResponse cancelDetails
) {
    public record CancelDetailsResponse(
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
            OffsetDateTime cancelledAt,
            CancelType cancelType,
            CancelReasonType reasonType,
            String reason
    ) {
        public static CancelDetailsResponse of(CancelCourseResult.CancelDetailsResult cancelDetailsResult) {
            return new CancelDetailsResponse(
                    DateTimeUtils.toKstDateTime(cancelDetailsResult.cancelledAt()),
                    cancelDetailsResult.cancelType(),
                    cancelDetailsResult.cancelReasonType(),
                    cancelDetailsResult.reason()
            );
        }
    }

    public static CancelCourseResponse of(CancelCourseResult result) {
        return new CancelCourseResponse(
                result.id(),
                result.courseId(),
                result.status(),
                DateTimeUtils.toKstDateTime(result.enrolledAt()),
                DateTimeUtils.toKstDateTime(result.activatedAt()),
                CancelDetailsResponse.of(result.cancelDetailsResult())
        );
    }
}
