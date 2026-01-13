package com.lxp.enrollment.application.port.provided.result;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record CancelCourseResult(
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsResult cancelDetailsResult
) {
    public static CancelCourseResult of(Enrollment enrollment) {
        return new CancelCourseResult(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                CancelDetailsResult.of(enrollment.cancelDetails())
        );
    }
}
