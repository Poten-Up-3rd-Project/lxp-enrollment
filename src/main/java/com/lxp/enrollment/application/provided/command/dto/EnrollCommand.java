package com.lxp.enrollment.application.provided.command.dto;

import java.util.UUID;

public record EnrollCommand(
        UUID userId,
        UUID courseId
) {
}
