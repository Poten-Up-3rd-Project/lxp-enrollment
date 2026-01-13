package com.lxp.enrollment.infra.provided.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;

import java.time.OffsetDateTime;

public record CancelDetailsResponse(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime cancelledAt,
        CancelType cancelType,
        CancelReasonType reasonType,
        String reason
) {
}
