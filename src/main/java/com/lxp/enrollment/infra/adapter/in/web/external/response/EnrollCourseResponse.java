package com.lxp.enrollment.infra.adapter.in.web.external.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

public record EnrollCourseResponse(
        UUID id,
        UUID userId,
        UUID courseId,
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Seoul")
        OffsetDateTime enrolledAt
) {
}
