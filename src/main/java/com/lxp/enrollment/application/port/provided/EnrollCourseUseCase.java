package com.lxp.enrollment.application.port.provided;

import com.lxp.enrollment.application.port.provided.dto.EnrollCourseResult;

import java.util.UUID;

public interface EnrollCourseUseCase {

    EnrollCourseResult enroll(UUID userId, UUID courseId);
}
