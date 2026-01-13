package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.common.application.port.in.CommandWithResultUseCase;
import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;

public interface CancelByUserUseCase extends CommandWithResultUseCase<CancelByUserCommand, CancelByUserSuccessView> {

    @Override
    CancelByUserSuccessView execute(CancelByUserCommand command);
}
