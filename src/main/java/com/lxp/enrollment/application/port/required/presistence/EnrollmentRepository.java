package com.lxp.enrollment.application.port.required.presistence;

import com.lxp.enrollment.domain.model.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository {
    Enrollment findById(UUID id);
    List<Enrollment> findAllByUserId(UUID userId);
    List<Enrollment> findAllByCourseId(UUID courseId);
    Enrollment create(Enrollment enrollment);
    Enrollment cancel(UUID userId, UUID courseId);
}
