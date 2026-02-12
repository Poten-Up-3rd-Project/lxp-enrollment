package com.lxp.enrollment.infra.provided.web.internal;

import com.lxp.enrollment.application.provided.query.EnrollmentSummaryForRecommendationQuery;
import com.lxp.enrollment.application.provided.query.dto.result.EnrollmentSummaryForRecommendationResult;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentSummariesForRecommendationQueryUseCase;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.passport.core.context.PassportContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.lxp.enrollment.domain.exception.EnrollmentErrorCode.INVALID_USER_ID;

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
        return PassportContext.getOptional()
            .map(claim -> UUID.fromString(claim.userId()))
            .orElseThrow(() -> new EnrollmentException(INVALID_USER_ID, "Passport 또는 uid 를 찾을 수 없습니다."));
    }
}
