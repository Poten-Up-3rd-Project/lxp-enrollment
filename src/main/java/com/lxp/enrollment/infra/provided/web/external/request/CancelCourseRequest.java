package com.lxp.enrollment.infra.provided.web.external.request;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import jakarta.validation.constraints.NotNull;

public record CancelCourseRequest(
        @NotNull(message = "취소 사유 유형은 필수값입니다.")
        CancelReasonType reasonType,
        String reason
) {
}
