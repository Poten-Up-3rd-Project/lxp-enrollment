package com.lxp.enrollment.application.provided.usecase;

import com.lxp.enrollment.application.provided.dto.command.EnrollCommand;
import com.lxp.enrollment.application.provided.dto.result.EnrollCourseResult;

public interface EnrollCourseUseCase {

    EnrollCourseResult enroll(EnrollCommand enrollCommand);
}
