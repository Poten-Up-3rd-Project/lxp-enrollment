package com.lxp.enrollment.infra.required.persistence.jpa.write;

import com.lxp.enrollment.application.required.presistence.write.EnrollmentWriteRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.infra.required.persistence.jpa.mapper.EnrollmentEntityMapper;
import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentWriteRepositoryJpaAdapter implements EnrollmentWriteRepository {

    private final EnrollmentWriteJpaRepository enrollmentWriteJpaRepository;
    private final EnrollmentEntityMapper enrollmentEntityMapper;

    public EnrollmentWriteRepositoryJpaAdapter(
            EnrollmentWriteJpaRepository enrollmentWriteJpaRepository,
            EnrollmentEntityMapper enrollmentEntityMapper
    ) {
        this.enrollmentWriteJpaRepository = enrollmentWriteJpaRepository;
        this.enrollmentEntityMapper = enrollmentEntityMapper;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {

        EnrollmentJpaEntity saved = enrollmentWriteJpaRepository.save(
                enrollmentEntityMapper.toEntity(enrollment)
        );

        return enrollmentEntityMapper.toDomain(saved);
    }
}
