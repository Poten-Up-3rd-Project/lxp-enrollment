package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.CancelCourseResult;

public interface CancelCourseByUserUseCase extends CommandWithResultUseCase<CancelByUserCommand, CancelCourseResult> {

    @Override
    CancelCourseResult execute(CancelByUserCommand command);
}
