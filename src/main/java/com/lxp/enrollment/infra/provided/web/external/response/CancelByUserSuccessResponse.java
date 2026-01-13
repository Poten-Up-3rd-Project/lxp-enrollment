package com.lxp.enrollment.infra.provided.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
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
        CancelDetailsOfCancelByUserSuccessResponse cancelDetails
) {
    public record CancelDetailsOfCancelByUserSuccessResponse(
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
            OffsetDateTime cancelledAt,
            CancelType cancelType,
            CancelReasonType reasonType,
            String reason
    ) {
    }
}
