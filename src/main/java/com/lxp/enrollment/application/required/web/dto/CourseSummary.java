package com.lxp.enrollment.application.required.web.dto;

import java.util.List;

public record CourseSummary(
        String courseId,
        String thumbnailUrl,
        Double totalProgress,
        String courseTitle,
        String courseDescription,
        String instructorName,
        String level,
        List<CourseTag> tags
) {
    public record CourseTag(
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
