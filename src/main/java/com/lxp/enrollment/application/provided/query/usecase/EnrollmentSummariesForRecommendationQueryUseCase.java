package com.lxp.enrollment.application.provided.query.usecase;

import com.lxp.common.application.port.in.QueryUseCase;
import com.lxp.enrollment.application.provided.query.EnrollmentSummaryForRecommendationQuery;
import com.lxp.enrollment.application.provided.query.dto.result.EnrollmentSummaryForRecommendationResult;

import java.util.List;

@FunctionalInterface
public interface EnrollmentSummariesForRecommendationQueryUseCase
        extends QueryUseCase<EnrollmentSummaryForRecommendationQuery, List<EnrollmentSummaryForRecommendationResult>> {
}
