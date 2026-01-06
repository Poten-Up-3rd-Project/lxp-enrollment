package com.lxp.enrollment.domain.repository;

import com.lxp.enrollment.domain.model.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository {
    Enrollment findById(UUID id);
    List<Enrollment> findAllByUserId(UUID userId);
    List<Enrollment> findAllByCourseId(UUID courseId);
    Enrollment save(Enrollment enrollment);
}
