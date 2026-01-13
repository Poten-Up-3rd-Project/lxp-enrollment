package com.lxp.enrollment.application.provided.usecase;

import com.lxp.enrollment.application.provided.dto.query.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.dto.result.EnrollmentDetailsQueryResult;

public interface EnrollmentQueryUseCase {

    EnrollmentDetailsQueryResult find(EnrollmentDetailsQuery query);
}
