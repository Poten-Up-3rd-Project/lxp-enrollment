package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.dto.EnrollCourseResult;

public interface EnrollCourseUseCase extends CommandWithResultUseCase<EnrollCommand, EnrollCourseResult> {

    @Override
    EnrollCourseResult execute(EnrollCommand command);
}
