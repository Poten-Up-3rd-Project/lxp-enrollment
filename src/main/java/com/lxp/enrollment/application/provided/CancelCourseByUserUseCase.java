package com.lxp.enrollment.application.provided;

import com.lxp.enrollment.application.provided.dto.result.CancelCourseResult;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;

import java.util.UUID;

public interface CancelCourseByUserUseCase {

    CancelCourseResult cancelByUser(UUID userId, UUID courseId, CancelReasonType cancelReasonType, String reason);
}
