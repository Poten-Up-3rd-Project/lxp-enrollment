package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.dto.EnrollCourseResult;

public interface EnrollCourseUseCase {

    EnrollCourseResult enroll(EnrollCommand command);
}
