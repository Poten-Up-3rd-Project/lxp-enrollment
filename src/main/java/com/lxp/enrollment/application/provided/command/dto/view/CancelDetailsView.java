package com.lxp.enrollment.application.provided.command.dto.view;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;

import java.time.Instant;

public record CancelDetailsView(
        Instant cancelledAt,
        CancelType cancelType,
        CancelReasonType cancelReasonType,
        String reason
) {
}
