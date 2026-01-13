package com.lxp.enrollment.application.port.provided.dto.result;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollCourseResult(
        UUID enrollmentId,
        UUID userId,
        UUID courseId,
        EnrollmentStatus enrollmentStatus,
        Instant enrolledAt
) {
    public static EnrollCourseResult of(Enrollment enrollment) {
        return new EnrollCourseResult(
                enrollment.id(),
                enrollment.userId(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt()
        );
    }
}
