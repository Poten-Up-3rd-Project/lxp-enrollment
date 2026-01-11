package com.lxp.enrollment.application.port.provided;

import com.lxp.enrollment.application.port.provided.dto.EnrollmentQueryResult;

import java.util.UUID;

public interface EnrollmentQueryUseCase {

    EnrollmentQueryResult find(UUID id);
}
