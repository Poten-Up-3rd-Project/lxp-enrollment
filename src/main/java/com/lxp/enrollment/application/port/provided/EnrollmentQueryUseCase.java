package com.lxp.enrollment.application.port.provided;

import com.lxp.enrollment.application.port.provided.result.EnrollmentQueryResult;

import java.util.UUID;

public interface EnrollmentQueryUseCase {

    EnrollmentQueryResult find(UUID userId, UUID courseId);
}
