package com.lxp.enrollment.infra.adapter.provided.web.external;

import com.lxp.common.passport.PassportClaims;
import com.lxp.common.passport.PassportVerifier;
import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.port.provided.EnrollCourseUseCase;
import com.lxp.enrollment.application.port.provided.dto.EnrollCourseResult;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.infra.adapter.provided.web.external.response.EnrollCourseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/api-v1/enrollments")
public class EnrollmentController {

    private final PassportVerifier passportVerifier;
    private final EnrollCourseUseCase enrollCourseUseCase;

    public EnrollmentController(
            PassportVerifier passportVerifier,
            EnrollCourseUseCase enrollCourseUseCase
    ) {
        this.passportVerifier = passportVerifier;
        this.enrollCourseUseCase = enrollCourseUseCase;
    }

    @PostMapping
    public ResponseEntity<EnrollCourseResponse> enrollCourse(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @RequestParam
            String courseId
    ) {
        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        EnrollCourseResult result = enrollCourseUseCase.enroll(userUuid, courseUuid);
        EnrollCourseResponse body = new EnrollCourseResponse(
                result.enrollmentId(),
                userUuid,
                courseUuid,
                result.enrollmentStatus().name(),
                DateTimeUtils.toKstDateTime(result.enrolledAt())
        );

        return ResponseEntity.ok(body);
    }

    // ---------- Helpers

    private UUID resolveUserId(String encodedPassport) {

        PassportClaims claims = passportVerifier.verify(encodedPassport);

        UUID userId;
        try {
            userId = UUID.fromString(claims.userId());
        } catch (IllegalArgumentException e) {
            throw new EnrollmentException(EnrollmentErrorCode.UNEXPECTED_USER_ID);
        }
        return userId;
    }

    private UUID resolveCourseId(String courseId) {
        try {
            return UUID.fromString(courseId);
        } catch (IllegalArgumentException e) {
            throw new EnrollmentException(EnrollmentErrorCode.UNEXPECTED_USER_ID);
        }
    }
}
