package com.lxp.enrollment.infra.provided.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EnrollSuccessResponse(
        UUID id,
        UUID userId,
        UUID courseId,
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime enrolledAt
) {
    public static EnrollSuccessResponse of(EnrollSuccessView result) {
        return new EnrollSuccessResponse(
                result.enrollmentId(),
                result.userId(),
                result.courseId(),
                result.enrollmentStatus().name(),
                DateTimeUtils.toKstDateTime(result.enrolledAt())
        );
    }
}
