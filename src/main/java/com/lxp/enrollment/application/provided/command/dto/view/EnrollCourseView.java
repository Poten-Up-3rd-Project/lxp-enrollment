package com.lxp.enrollment.application.provided.command.dto.view;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollCourseView(
        UUID enrollmentId,
        UUID userId,
        UUID courseId,
        EnrollmentStatus enrollmentStatus,
        Instant enrolledAt
) {
    public static EnrollCourseView of(Enrollment enrollment) {
        return new EnrollCourseView(
                enrollment.id(),
                enrollment.userId(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt()
        );
    }
}
