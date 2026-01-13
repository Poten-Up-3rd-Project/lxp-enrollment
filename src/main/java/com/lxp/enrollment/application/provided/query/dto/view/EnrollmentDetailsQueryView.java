package com.lxp.enrollment.application.provided.query.dto.view;

import com.lxp.enrollment.application.provided.command.dto.view.CancelDetailsView;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollmentDetailsQueryView(
        // To Do: course, progress, tag 정보 포함 시켜야 함
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        CancelDetailsView cancelDetailsView
) {
    public static EnrollmentDetailsQueryView of(Enrollment enrollment) {
        return new EnrollmentDetailsQueryView(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                CancelDetailsView.of(enrollment.cancelDetails())
        );
    }
}
