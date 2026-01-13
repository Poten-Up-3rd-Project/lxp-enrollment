package com.lxp.enrollment.application.provided.dto.command;

import java.util.UUID;

public record EnrollCommand(
        UUID userId,
        UUID courseId
) {
}
