package com.lxp.enrollment.infra.adapter.in.web.external.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollCourseRequest(
        @NotNull(message = "userId 가 null 입니다.")
        UUID userId,
        @NotNull(message = "courseId 가 null 입니다.")
        UUID courseId
) {
}
