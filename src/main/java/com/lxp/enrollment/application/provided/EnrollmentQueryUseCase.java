package com.lxp.enrollment.application.provided;

import com.lxp.enrollment.application.provided.dto.result.EnrollmentDetailsQueryResult;

import java.util.UUID;

public interface EnrollmentQueryUseCase {

    EnrollmentDetailsQueryResult find(UUID userId, UUID courseId);
}
