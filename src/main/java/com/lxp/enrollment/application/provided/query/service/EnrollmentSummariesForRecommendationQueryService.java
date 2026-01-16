package com.lxp.enrollment.application.provided.query.service;

import com.lxp.enrollment.application.provided.query.EnrollmentSummaryForRecommendationQuery;
import com.lxp.enrollment.application.provided.query.dto.result.EnrollmentSummaryForRecommendationResult;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentSummariesForRecommendationQueryUseCase;
import com.lxp.enrollment.application.required.presistence.read.EnrollmentReadRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentSummariesForRecommendationQueryService implements EnrollmentSummariesForRecommendationQueryUseCase {

    private EnrollmentReadRepository enrollmentReadRepository;

    public EnrollmentSummariesForRecommendationQueryService(EnrollmentReadRepository enrollmentReadRepository) {
        this.enrollmentReadRepository = enrollmentReadRepository;
    }

    @Override
    public List<EnrollmentSummaryForRecommendationResult> execute(EnrollmentSummaryForRecommendationQuery query) {
        return enrollmentReadRepository.findAllByUserId(query.userId())
                .stream()
                .map(this::toResult)
                .toList();
    }

    private EnrollmentSummaryForRecommendationResult toResult(Enrollment enrollment) {
        return new EnrollmentSummaryForRecommendationResult(
                enrollment.userId().toString(),
                enrollment.courseId().toString(),
                enrollment.enrollmentStatus().name()
        );
    }
}
