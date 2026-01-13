package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelCourseView;

public interface CancelCourseByUserUseCase extends CommandWithResultUseCase<CancelByUserCommand, CancelCourseView> {

    @Override
    CancelCourseView execute(CancelByUserCommand command);
}
