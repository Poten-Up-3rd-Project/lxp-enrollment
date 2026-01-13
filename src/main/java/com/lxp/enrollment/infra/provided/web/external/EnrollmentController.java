package com.lxp.enrollment.infra.provided.web.external;

import com.lxp.enrollment.application.port.provided.CancelCourseByUserUseCase;
import com.lxp.enrollment.application.port.provided.EnrollmentQueryUseCase;
import com.lxp.enrollment.application.port.provided.result.CancelCourseResult;
import com.lxp.enrollment.application.port.provided.result.EnrollmentDetailsQueryResult;
import com.lxp.enrollment.infra.provided.web.external.passport.PassportClaims;
import com.lxp.enrollment.infra.provided.web.external.passport.PassportVerifier;
import com.lxp.enrollment.application.port.provided.EnrollCourseUseCase;
import com.lxp.enrollment.application.port.provided.result.EnrollCourseResult;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.infra.provided.web.external.request.CancelCourseRequest;
import com.lxp.enrollment.infra.provided.web.external.response.CancelCourseResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollCourseResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api-v1/enrollments")
public class EnrollmentController {

    private final PassportVerifier passportVerifier;
    private final EnrollCourseUseCase enrollCourseUseCase;
    private final CancelCourseByUserUseCase cancelCourseByUserUseCase;
    private final EnrollmentQueryUseCase enrollmentQueryUseCase;

    public EnrollmentController(
            PassportVerifier passportVerifier,
            EnrollCourseUseCase enrollCourseUseCase,
            CancelCourseByUserUseCase cancelCourseByUserUseCase,
            EnrollmentQueryUseCase enrollmentQueryUseCase
    ) {
        this.passportVerifier = passportVerifier;
        this.enrollCourseUseCase = enrollCourseUseCase;
        this.cancelCourseByUserUseCase = cancelCourseByUserUseCase;
        this.enrollmentQueryUseCase = enrollmentQueryUseCase;
    }

    // ---------- 수강 등록 요청 핸들러

    @PostMapping
    public ResponseEntity<EnrollCourseResponse> enroll(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @RequestParam
            String courseId
    ) {
        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        EnrollCourseResult result = enrollCourseUseCase.enroll(userUuid, courseUuid);
        EnrollCourseResponse body = EnrollCourseResponse.of(result);

        return ResponseEntity.ok(body);
    }

    // ---------- 수강 취소 요청 핸들러

    @PostMapping("/cancel")
    public ResponseEntity<CancelCourseResponse> cancelByUser(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @RequestParam
            String courseId,
            @RequestBody
            @Valid
            CancelCourseRequest request
    ) {
        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        CancelCourseResult result = cancelCourseByUserUseCase.cancelByUser(
                userUuid,
                courseUuid,
                request.reasonType(),
                request.reason()
        );
        CancelCourseResponse body = CancelCourseResponse.of(result);

        return ResponseEntity.ok(body);
    }

    // ---------- 나의 수강 상세 조회

    @GetMapping("/{courseId}")
    public ResponseEntity<EnrollmentDetailsResponse> getEnrollmentDetails(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @PathVariable
            String courseId
    ) {

        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        EnrollmentDetailsQueryResult result = enrollmentQueryUseCase.find(userUuid, courseUuid);
        EnrollmentDetailsResponse body = EnrollmentDetailsResponse.of(result);

        return ResponseEntity.ok(body);
    }

    // ---------- Helpers

    private UUID resolveUserId(String encodedPassport) {

        PassportClaims claims = passportVerifier.verify(encodedPassport);

        UUID userId;
        try {
            userId = UUID.fromString(claims.userId());
        } catch (IllegalArgumentException e) {
            throw new EnrollmentException(EnrollmentErrorCode.INVALID_USER_ID);
        }
        return userId;
    }

    private UUID resolveCourseId(String courseId) {
        try {
            return UUID.fromString(courseId);
        } catch (IllegalArgumentException e) {
            throw new EnrollmentException(EnrollmentErrorCode.INVALID_USER_ID);
        }
    }

    private UUID resolveEnrollmentId(String enrollmentId) {
        try {
            return UUID.fromString(enrollmentId);
        } catch (IllegalArgumentException e) {
            throw new EnrollmentException(EnrollmentErrorCode.INVALID_USER_ID);
        }
    }
}
