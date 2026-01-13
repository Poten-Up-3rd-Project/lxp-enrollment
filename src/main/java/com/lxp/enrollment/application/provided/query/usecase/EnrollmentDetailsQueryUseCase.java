package com.lxp.enrollment.application.provided.query.usecase;

import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQueryResult;

public interface EnrollmentDetailsQueryUseCase {

    EnrollmentDetailsQueryResult find(EnrollmentDetailsQuery query);
}
