package com.lxp.enrollment.application.port.provided.dto;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;
import com.lxp.enrollment.domain.model.vo.CancelDetails;

import java.time.Instant;
import java.util.UUID;

public record CancelCourseResult(
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsResult cancelDetailsResult
) {
    public record CancelDetailsResult(
            Instant cancelledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String reason
    ) {
        public static CancelDetailsResult of(CancelDetails cancelDetails) {
            return new CancelDetailsResult(
                    cancelDetails.cancelledAt(),
                    cancelDetails.cancelType(),
                    cancelDetails.cancelReasonType(),
                    cancelDetails.cancelReasonComment()
            );
        }
    }

    public static CancelCourseResult of(Enrollment enrollment) {
        return new CancelCourseResult(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                CancelDetailsResult.of(enrollment.cancelDetails())
        );
    }
}
