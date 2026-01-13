package com.lxp.enrollment.application.provided.query.dto.view;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollmentDetailsQueryView(
        // To Do: course, progress, tag 정보 포함 시켜야 함
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsOfEnrollmentDetailsQueryView cancelDetailsOfEnrollmentDetailsQueryView
) {
    public record CancelDetailsOfEnrollmentDetailsQueryView(
            Instant cancelledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String reason
    ) {
    }
}
