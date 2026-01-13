package com.lxp.enrollment.infra.required.persistence.jpa.write;

import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentWriteJpaRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {
}
