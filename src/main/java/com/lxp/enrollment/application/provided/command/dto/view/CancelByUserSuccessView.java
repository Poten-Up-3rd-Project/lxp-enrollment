package com.lxp.enrollment.application.provided.command.dto.view;

import com.lxp.enrollment.domain.model.Enrollment;
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
    public static CancelByUserSuccessView of(Enrollment enrollment) {
        return new CancelByUserSuccessView(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                CancelDetailsView.of(enrollment.cancelDetails())
        );
    }
}
