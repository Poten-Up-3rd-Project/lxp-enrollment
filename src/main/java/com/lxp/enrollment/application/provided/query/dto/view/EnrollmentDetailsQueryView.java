package com.lxp.enrollment.application.provided.query.dto.view;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record EnrollmentDetailsQueryView(
        // enrollment
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        Instant enrolledAt,
        Instant activatedAt,
        // enrollment - cancel
        CancelDetailsOfEnrollmentDetailsQueryView cancelDetailsOfEnrollmentDetailsQueryView,
        // course
        CourseSummaryView courseSummaryView
) {
    public record CancelDetailsOfEnrollmentDetailsQueryView(
            Instant cancelledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String reason
    ) {
    }

    public record CourseSummaryView(
            String thumbnailUrl,
            Double totalProgress,
            String courseTitle,
            String courseDescription,
            String instructorName,
            String level,
            List<CourseTagView> tagsView
    ) {
        public record CourseTagView(
                String category,
                String subCategory,
                Long tagId,
                String name,
                String state,
                String color,
                String variant
        ) {
        }
    }
}
