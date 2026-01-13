package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;

public interface EnrollUseCase extends CommandWithResultUseCase<EnrollCommand, EnrollSuccessView> {

    @Override
    EnrollSuccessView execute(EnrollCommand command);
}
