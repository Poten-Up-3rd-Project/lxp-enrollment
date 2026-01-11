package com.lxp.enrollment.infra.adapter.required.persistence.jpa;

import com.lxp.enrollment.infra.adapter.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {
    Optional<EnrollmentJpaEntity> findByUserIdAndCourseId(UUID userId, UUID courseId);

    List<EnrollmentJpaEntity> findAllByUserId(UUID userId);

    List<EnrollmentJpaEntity> findAllByCourseId(UUID courseId);
}
