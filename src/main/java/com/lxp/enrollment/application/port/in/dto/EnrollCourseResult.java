package com.lxp.enrollment.application.port.in.dto;

import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollCourseResult(
        UUID enrollmentId,
        EnrollmentStatus enrollmentStatus,
        Instant enrolledAt
) {
}
