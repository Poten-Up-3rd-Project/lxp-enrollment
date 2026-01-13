package com.lxp.enrollment.application.provided.dto.query;

import java.util.UUID;

public record EnrollmentDetailsQuery(
        UUID userId,
        UUID courseId
) {
}
