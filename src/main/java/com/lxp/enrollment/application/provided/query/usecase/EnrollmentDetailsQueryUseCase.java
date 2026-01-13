package com.lxp.enrollment.application.provided.query.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQueryResult;

public interface EnrollmentDetailsQueryUseCase extends QueryUseCase<EnrollmentDetailsQuery, EnrollmentDetailsQueryResult> {

    @Override
    EnrollmentDetailsQueryResult execute(EnrollmentDetailsQuery query);
}
