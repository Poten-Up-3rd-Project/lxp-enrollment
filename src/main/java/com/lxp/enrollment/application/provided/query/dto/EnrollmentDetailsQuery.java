package com.lxp.enrollment.application.provided.query.dto;

import java.util.UUID;

public record EnrollmentDetailsQuery(
        UUID userId,
        UUID courseId
) {
}
