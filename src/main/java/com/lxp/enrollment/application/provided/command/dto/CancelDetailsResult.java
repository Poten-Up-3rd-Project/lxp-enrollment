package com.lxp.enrollment.application.provided.command.dto;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.vo.CancelDetails;

import java.time.Instant;

public record CancelDetailsResult(
        Instant cancelledAt,
        CancelType cancelType,
        CancelReasonType cancelReasonType,
        String reason
) {
    public static CancelDetailsResult of(CancelDetails cancelDetails) {
        if (cancelDetails == null) {
            return null;
        }

        return new CancelDetailsResult(
                cancelDetails.cancelledAt(),
                cancelDetails.cancelType(),
                cancelDetails.cancelReasonType(),
                cancelDetails.cancelReasonComment()
        );
    }
}
