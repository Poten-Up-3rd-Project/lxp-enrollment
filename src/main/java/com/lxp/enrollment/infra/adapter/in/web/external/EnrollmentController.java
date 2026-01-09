package com.lxp.enrollment.infra.adapter.in.web.external;

import com.lxp.common.infrastructure.exception.ApiResponse;
import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.port.in.EnrollCourseUseCase;
import com.lxp.enrollment.application.port.in.dto.EnrollCourseResult;
import com.lxp.enrollment.infra.adapter.in.web.external.request.EnrollCourseRequest;
import com.lxp.enrollment.infra.adapter.in.web.external.response.EnrollCourseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
@RequestMapping("/api-v1/enrollments")
public class EnrollmentController {

    private final EnrollCourseUseCase enrollCourseUseCase;

    public EnrollmentController(EnrollCourseUseCase enrollCourseUseCase) {
        this.enrollCourseUseCase = enrollCourseUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EnrollCourseResponse>> enrollCourse(
            @RequestBody @Valid EnrollCourseRequest request
    ) {
        EnrollCourseResult result = enrollCourseUseCase.enroll(request.userId(), request.courseId());
        EnrollCourseResponse body = new EnrollCourseResponse(
                result.enrollmentId(),
                request.userId(),
                request.courseId(),
                result.enrollmentStatus().name(),
                DateTimeUtils.toKstDateTime(result.enrolledAt())
        );

        return ResponseEntity.ok(ApiResponse.success(body));
    }
}
