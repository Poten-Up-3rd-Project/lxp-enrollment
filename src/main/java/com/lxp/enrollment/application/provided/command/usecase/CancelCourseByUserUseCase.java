package com.lxp.enrollment.application.provided.command.usecase;

import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.CancelCourseResult;

public interface CancelCourseByUserUseCase {

    CancelCourseResult cancelByUser(CancelByUserCommand command);
}
