package com.lxp.enrollment.application.required.presistence.read;

import com.lxp.common.domain.pagination.Page;
import com.lxp.common.domain.pagination.PageRequest;
import com.lxp.enrollment.domain.model.Enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentReadRepository {
    Optional<Enrollment> findById(UUID id);

    Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);

    Page<Enrollment> findAllByUserId(UUID userId, PageRequest pageRequest);

    List<Enrollment> findAllByUserId(UUID userId);

    List<Enrollment> findAllByCourseId(UUID courseId);
}
