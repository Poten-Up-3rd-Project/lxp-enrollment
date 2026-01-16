package com.lxp.enrollment.infra.required.persistence.jpa.read;

import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentReadJpaRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {
    Optional<EnrollmentJpaEntity> findByUserIdAndCourseId(UUID userId, UUID courseId);

    List<EnrollmentJpaEntity> findAllByUserId(UUID userId);

    Page<EnrollmentJpaEntity> findAllByUserId(UUID userId, Pageable pageable);

    List<EnrollmentJpaEntity> findAllByCourseId(UUID courseId);
}
