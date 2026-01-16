package com.lxp.enrollment.infra.provided.web.internal;

import com.lxp.common.passport.exception.InvalidPassportException;
import com.lxp.enrollment.application.provided.query.EnrollmentSummaryForRecommendationQuery;
import com.lxp.enrollment.application.provided.query.dto.result.EnrollmentSummaryForRecommendationResult;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentSummariesForRecommendationQueryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/internal/api-v1/enrollments")
public class EnrollmentInternalController {

    private final EnrollmentSummariesForRecommendationQueryUseCase enrollmentSummariesForRecommendationQueryUseCase;

    public EnrollmentInternalController(EnrollmentSummariesForRecommendationQueryUseCase enrollmentSummariesForRecommendationQueryUseCase) {
        this.enrollmentSummariesForRecommendationQueryUseCase = enrollmentSummariesForRecommendationQueryUseCase;
    }

    @GetMapping("/myEnrollmentsForRecommendation")
    public ResponseEntity<List<EnrollmentSummaryForRecommendationResult>> getMyEnrollmentsForRecommendation() {

        UUID userId = resolveUserId();
        EnrollmentSummaryForRecommendationQuery q = new EnrollmentSummaryForRecommendationQuery(userId);
        List<EnrollmentSummaryForRecommendationResult> result = enrollmentSummariesForRecommendationQueryUseCase.execute(q);

        return ResponseEntity.ok(result);
    }

    private UUID resolveUserId() {

        try {
            String uid = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal()
                    .toString();
            return UUID.fromString(uid);
        } catch (Exception ignore) {
            throw new InvalidPassportException("Passport 또는 uid 를 찾을 수 없습니다.");
        }
    }
}
