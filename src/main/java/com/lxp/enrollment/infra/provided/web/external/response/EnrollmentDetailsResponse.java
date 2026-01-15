package com.lxp.enrollment.infra.provided.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record EnrollmentDetailsResponse(
        // enrollment
        UUID id,
        UUID courseId,
        EnrollmentStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime enrolledAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime activatedAt,
        // enrollment - cancel
        CancelDetailsOfEnrollmentDetailsResponse cancelDetails,
        // course
        String thumbnailUrl,
        Double totalProgress,
        String courseTitle,
        String courseDescription,
        String instructorName,
        String level,
        List<CourseSummaryResponse.CourseTagResponse> tags
) {

    public record CancelDetailsOfEnrollmentDetailsResponse(
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX", timezone = "Asia/Seoul")
            OffsetDateTime cancelledAt,
            CancelType cancelType,
            CancelReasonType reasonType,
            String reason
    ) {
    }

    public record CourseSummaryResponse(
            String thumbnailUrl,
            Double totalProgress,
            String courseTitle,
            String courseDescription,
            String instructorName,
            String level,
            List<CourseTagResponse> tags
    ) {
        public record CourseTagResponse(
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
