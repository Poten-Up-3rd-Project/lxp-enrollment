package com.lxp.enrollment.application.provided;

import com.lxp.enrollment.application.provided.dto.result.EnrollCourseResult;

import java.util.UUID;

public interface EnrollCourseUseCase {

    EnrollCourseResult enroll(UUID userId, UUID courseId);
}
