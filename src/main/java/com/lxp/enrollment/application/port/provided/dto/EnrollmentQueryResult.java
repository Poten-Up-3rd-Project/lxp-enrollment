package com.lxp.enrollment.application.port.provided.dto;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollmentQueryResult(
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsResult cancelDetailsResult
) {
    public static EnrollmentQueryResult of(Enrollment enrollment) {
        return new EnrollmentQueryResult(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                CancelDetailsResult.of(enrollment.cancelDetails())
        );
    }
}
