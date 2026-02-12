package com.lxp.enrollment.application.event.integration.payload;

public record EnrollmentEventPayload(
    String courseId,
    String userId
) {
}
