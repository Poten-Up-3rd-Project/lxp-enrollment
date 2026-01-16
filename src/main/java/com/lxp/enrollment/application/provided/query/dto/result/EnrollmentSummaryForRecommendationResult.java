package com.lxp.enrollment.application.provided.query.dto.result;

public record EnrollmentSummaryForRecommendationResult(
        String learnerId,
        String courseId,
        String status
) {
}
