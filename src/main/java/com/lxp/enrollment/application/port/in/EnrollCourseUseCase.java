package com.lxp.enrollment.application.port.in;

import com.lxp.enrollment.application.port.in.dto.EnrollCourseResult;

import java.util.UUID;

public interface EnrollCourseUseCase {

    EnrollCourseResult enroll(UUID userId, UUID courseId);
}
