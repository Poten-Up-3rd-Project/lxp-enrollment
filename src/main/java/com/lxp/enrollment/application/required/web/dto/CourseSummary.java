package com.lxp.enrollment.application.required.web.dto;

import java.time.Instant;
import java.util.List;

public record CourseSummary(
        String courseId,
        String thumbnailUrl,
        Double totalProgress,
        String title,
        String description,
        CourseInstructor Instructor,
        String level,
        Instant createdAt,
        Instant updatedAt,
        List<CourseTag> tags
) {
    // content == 태그 이름
    // To Do: 태그 - category, subCategory, State 필요하다고 민영님한테 말씀드릴 것
    // To Do: 강사 id 도 응답에 포함 시키자
    public record CourseTag(
            String category,
            String subCategory,
            Long id,
            String content,
            String color,
            String variant
    ) {
    }

    public record CourseInstructor(
            String instructorId,
            String name
    ) {
    }
}
