package com.lxp.enrollment.infra.provided.web.external;

import com.lxp.common.domain.pagination.Page;
import com.lxp.common.infrastructure.persistence.PageConverter;
import com.lxp.common.passport.exception.InvalidPassportException;
import com.lxp.common.passport.support.PassportVerifier;
import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.command.usecase.CancelByUserUseCase;
import com.lxp.enrollment.application.provided.command.usecase.EnrollUseCase;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentSummariesQuery;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentSummaryQueryView;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentDetailsQueryUseCase;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentSummariesQueryUseCase;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.infra.provided.web.external.mapper.EnrollmentResponseMapper;
import com.lxp.enrollment.infra.provided.web.external.request.CancelRequest;
import com.lxp.enrollment.infra.provided.web.external.response.CancelByUserSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final EnrollmentSummariesQueryUseCase enrollmentSummariesQueryUseCase;
    private final EnrollmentResponseMapper enrollmentResponseMapper;

    public EnrollmentController(
            PassportVerifier passportVerifier,
            EnrollUseCase enrollUseCase,
            CancelByUserUseCase cancelByUserUseCase,
            EnrollmentDetailsQueryUseCase enrollmentDetailsQueryUseCase,
            EnrollmentSummariesQueryUseCase enrollmentSummariesQueryUseCase,
            EnrollmentResponseMapper enrollmentResponseMapper
    ) {
        this.passportVerifier = passportVerifier;
        this.enrollUseCase = enrollUseCase;
        this.cancelByUserUseCase = cancelByUserUseCase;
        this.enrollmentDetailsQueryUseCase = enrollmentDetailsQueryUseCase;
        this.enrollmentSummariesQueryUseCase = enrollmentSummariesQueryUseCase;
        this.enrollmentResponseMapper = enrollmentResponseMapper;
    }

    // ---------- 수강 등록 요청 핸들러

    @PostMapping
    public ResponseEntity<EnrollSuccessResponse> enroll(
            @RequestParam
            String courseId
    ) {
        UUID userUuid = resolveUserId();
        UUID courseUuid = resolveCourseId(courseId);

        EnrollCommand command = new EnrollCommand(userUuid, courseUuid);
        EnrollSuccessView view = enrollUseCase.execute(command);
        EnrollSuccessResponse body = enrollmentResponseMapper.toEnrollSuccessResponse(view);

        return ResponseEntity.ok(body);
    }

    // ---------- 수강 취소 요청 핸들러

    @PostMapping("/cancel")
    public ResponseEntity<CancelByUserSuccessResponse> cancelByUser(
            @RequestParam
            String courseId,
            @RequestBody
            @Valid
            CancelRequest request
    ) {
        UUID userUuid = resolveUserId();
        UUID courseUuid = resolveCourseId(courseId);

        CancelByUserCommand command = new CancelByUserCommand(
                userUuid,
                courseUuid,
                request.reasonType(),
                request.reason()
        );

        CancelByUserSuccessView view = cancelByUserUseCase.execute(command);
        CancelByUserSuccessResponse body = enrollmentResponseMapper.toCancelByUserSuccessResponse(view);

        return ResponseEntity.ok(body);
    }

    // ---------- 나의 수강 상세 조회

    @GetMapping("/{courseId}")
    public ResponseEntity<EnrollmentDetailsResponse> myEnrollmentDetailsOf(
            @PathVariable
            String courseId
    ) {

        UUID userUuid = resolveUserId();
        UUID courseUuid = resolveCourseId(courseId);

        EnrollmentDetailsQuery query = new EnrollmentDetailsQuery(userUuid, courseUuid);
        EnrollmentDetailsQueryView view = enrollmentDetailsQueryUseCase.execute(query);
        EnrollmentDetailsResponse body = enrollmentResponseMapper.toEnrollmentDetailsResponse(view);

        return ResponseEntity.ok(body);
    }

    // ---------- 나의 수강 목록 조회

    @GetMapping
    public ResponseEntity<Page<EnrollmentSummaryResponse>> myEnrollments(
            @PageableDefault(size = 20, sort = "enrolledAt", direction = Sort.Direction.DESC)
            Pageable request
    ) {

        UUID userUuid = resolveUserId();

        EnrollmentSummariesQuery query = new EnrollmentSummariesQuery(
                userUuid,
                PageConverter.toDomainPageRequest(request)
        );
        Page<EnrollmentSummaryQueryView> view = enrollmentSummariesQueryUseCase.execute(query);
        Page<EnrollmentSummaryResponse> body = enrollmentResponseMapper.toEnrollmentSummariesResponse(view);

        return ResponseEntity.ok(body);
    }

    // ---------- Helpers

    // To Do: 나중에 컨트롤러 여러 개로 분리하게 되면 아래 메서드들도 별도 클래스로 분리하는 게 좋을 것 같음
    
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
