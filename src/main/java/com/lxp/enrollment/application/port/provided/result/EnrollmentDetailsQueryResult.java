package com.lxp.enrollment.application.port.provided.result;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollmentDetailsQueryResult(
        // To Do: course, progress, tag 정보 포함 시켜야 함
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsResult cancelDetailsResult
) {
    public static EnrollmentDetailsQueryResult of(Enrollment enrollment) {
        return new EnrollmentDetailsQueryResult(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                CancelDetailsResult.of(enrollment.cancelDetails())
        );
    }
}
