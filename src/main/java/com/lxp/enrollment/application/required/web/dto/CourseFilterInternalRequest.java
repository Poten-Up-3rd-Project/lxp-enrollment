package com.lxp.enrollment.application.required.web.dto;

import jakarta.annotation.Nullable;

import java.util.List;

public record CourseFilterInternalRequest(
        List<String> ids,
        @Nullable List<String> difficulties,
        int limit
) {
}
