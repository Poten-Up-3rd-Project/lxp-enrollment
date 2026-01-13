package com.lxp.enrollment.application.provided.usecase;

import com.lxp.enrollment.application.provided.dto.command.CancelByUserCommand;
import com.lxp.enrollment.application.provided.dto.result.CancelCourseResult;

public interface CancelCourseByUserUseCase {

    CancelCourseResult cancelByUser(CancelByUserCommand command);
}
