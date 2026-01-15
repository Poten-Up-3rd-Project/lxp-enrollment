package com.lxp.enrollment.application.provided.command.dto.view;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record CancelByUserSuccessView(
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsView cancelDetailsView
) {
    public record CancelDetailsView(
            Instant cancelledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String reason
    ) {
    }
}
