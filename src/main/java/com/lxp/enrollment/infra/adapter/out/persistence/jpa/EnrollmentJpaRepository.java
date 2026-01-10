package com.lxp.enrollment.infra.adapter.out.persistence.jpa;

import com.lxp.enrollment.infra.adapter.out.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {
}
