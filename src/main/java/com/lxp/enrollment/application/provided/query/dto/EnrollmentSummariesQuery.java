package com.lxp.enrollment.application.provided.query.dto;

import com.lxp.common.domain.pagination.PageRequest;

import java.util.UUID;

public record EnrollmentSummariesQuery(
        UUID userId,
        PageRequest pageRequest
) {
}
