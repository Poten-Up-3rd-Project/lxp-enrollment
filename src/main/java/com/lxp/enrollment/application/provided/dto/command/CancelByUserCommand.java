package com.lxp.enrollment.application.provided.dto.command;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;

import java.util.UUID;

public record CancelByUserCommand(
        UUID userId,
        UUID courseId,
        CancelReasonType cancelReasonType,
        String reason
) {
}
