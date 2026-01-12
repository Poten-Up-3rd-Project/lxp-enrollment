package com.lxp.enrollment.application.port.required.presistence;

import com.lxp.enrollment.domain.model.Enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {
    Optional<Enrollment> findById(UUID id);

    Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);

    List<Enrollment> findAllByUserId(UUID userId);

    List<Enrollment> findAllByCourseId(UUID courseId);

    Enrollment save(Enrollment enrollment);
}
