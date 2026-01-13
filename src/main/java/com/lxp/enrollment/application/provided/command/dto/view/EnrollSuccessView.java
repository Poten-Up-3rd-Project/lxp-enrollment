package com.lxp.enrollment.application.provided.command.dto.view;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollSuccessView(
        UUID enrollmentId,
        UUID userId,
        UUID courseId,
        EnrollmentStatus enrollmentStatus,
        Instant enrolledAt
) {
}
