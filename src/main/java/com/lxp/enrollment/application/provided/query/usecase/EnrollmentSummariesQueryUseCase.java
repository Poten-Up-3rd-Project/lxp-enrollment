package com.lxp.enrollment.application.provided.query.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.common.domain.pagination.Page;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentSummariesQuery;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentSummaryQueryView;

public interface EnrollmentSummariesQueryUseCase extends QueryUseCase<EnrollmentSummariesQuery, Page<EnrollmentSummaryQueryView>> {
    @Override
    Page<EnrollmentSummaryQueryView> execute(EnrollmentSummariesQuery query);
}
