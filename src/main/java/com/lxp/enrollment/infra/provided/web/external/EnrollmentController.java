package com.lxp.enrollment.infra.provided.web.external;

import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.command.usecase.CancelByUserUseCase;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentDetailsQueryUseCase;
import com.lxp.enrollment.application.provided.command.dto.view.CancelSuccessView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.infra.provided.web.external.passport.PassportClaims;
import com.lxp.enrollment.infra.provided.web.external.passport.PassportVerifier;
import com.lxp.enrollment.application.provided.command.usecase.EnrollUseCase;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.infra.provided.web.external.request.CancelRequest;
import com.lxp.enrollment.infra.provided.web.external.response.CancelSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollSuccessResponse;
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
    private final EnrollUseCase enrollUseCase;
    private final CancelByUserUseCase cancelByUserUseCase;
    private final EnrollmentDetailsQueryUseCase enrollmentDetailsQueryUseCase;

    public EnrollmentController(
            PassportVerifier passportVerifier,
            EnrollUseCase enrollUseCase,
            CancelByUserUseCase cancelByUserUseCase,
            EnrollmentDetailsQueryUseCase enrollmentDetailsQueryUseCase
    ) {
        this.passportVerifier = passportVerifier;
        this.enrollUseCase = enrollUseCase;
        this.cancelByUserUseCase = cancelByUserUseCase;
        this.enrollmentDetailsQueryUseCase = enrollmentDetailsQueryUseCase;
    }

    // ---------- 수강 등록 요청 핸들러

    @PostMapping
    public ResponseEntity<EnrollSuccessResponse> enroll(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @RequestParam
            String courseId
    ) {
        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        EnrollCommand command = new EnrollCommand(userUuid, courseUuid);
        EnrollSuccessView view = enrollUseCase.execute(command);
        EnrollSuccessResponse body = EnrollSuccessResponse.of(view);

        return ResponseEntity.ok(body);
    }

    // ---------- 수강 취소 요청 핸들러

    @PostMapping("/cancel")
    public ResponseEntity<CancelSuccessResponse> cancelByUser(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @RequestParam
            String courseId,
            @RequestBody
            @Valid
            CancelRequest request
    ) {
        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        CancelByUserCommand command = new CancelByUserCommand(
                userUuid,
                courseUuid,
                request.reasonType(),
                request.reason()
        );

        CancelSuccessView view = cancelByUserUseCase.execute(command);
        CancelSuccessResponse body = CancelSuccessResponse.of(view);

        return ResponseEntity.ok(body);
    }

    // ---------- 나의 수강 상세 조회

    @GetMapping("/{courseId}")
    public ResponseEntity<EnrollmentDetailsResponse> myEnrollmentDetailsOf(
            @RequestHeader("X-Passport")
            String encodedPassport,
            @PathVariable
            String courseId
    ) {

        UUID userUuid = resolveUserId(encodedPassport);
        UUID courseUuid = resolveCourseId(courseId);

        EnrollmentDetailsQuery query = new EnrollmentDetailsQuery(userUuid, courseUuid);
        EnrollmentDetailsQueryView view = enrollmentDetailsQueryUseCase.execute(query);
        EnrollmentDetailsResponse body = EnrollmentDetailsResponse.of(view);

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
