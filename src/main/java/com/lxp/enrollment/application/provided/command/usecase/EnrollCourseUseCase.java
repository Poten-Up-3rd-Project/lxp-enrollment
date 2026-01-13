package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollCourseView;

public interface EnrollCourseUseCase extends CommandWithResultUseCase<EnrollCommand, EnrollCourseView> {

    @Override
    EnrollCourseView execute(EnrollCommand command);
}
