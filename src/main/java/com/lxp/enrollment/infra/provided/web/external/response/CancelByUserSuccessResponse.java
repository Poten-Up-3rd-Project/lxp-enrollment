package com.lxp.enrollment.infra.provided.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CancelByUserSuccessResponse(
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime enrolledAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime activatedAt,
        CancelDetailsResponse cancelDetails
) {
    public static CancelByUserSuccessResponse of(CancelByUserSuccessView result) {
        return new CancelByUserSuccessResponse(
                result.id(),
                result.courseId(),
                result.status(),
                DateTimeUtils.toKstDateTime(result.enrolledAt()),
                DateTimeUtils.toKstDateTime(result.activatedAt()),
                CancelDetailsResponse.of(result.cancelDetailsView())
        );
    }
}
