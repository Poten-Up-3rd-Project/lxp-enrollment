package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelSuccessView;

public interface CancelByUserUseCase extends CommandWithResultUseCase<CancelByUserCommand, CancelSuccessView> {

    @Override
    CancelSuccessView execute(CancelByUserCommand command);
}
