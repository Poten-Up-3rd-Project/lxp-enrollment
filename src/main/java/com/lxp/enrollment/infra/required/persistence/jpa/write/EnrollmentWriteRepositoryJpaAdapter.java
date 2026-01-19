package com.lxp.enrollment.infra.required.persistence.jpa.write;

import com.lxp.enrollment.application.required.presistence.write.EnrollmentWriteRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.infra.required.persistence.jpa.mapper.EnrollmentEntityMapper;
import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.dao.DataIntegrityViolationException;
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

        try {
            EnrollmentJpaEntity saved = enrollmentWriteJpaRepository.save(
                    enrollmentEntityMapper.toEntity(enrollment)
            );
            return enrollmentEntityMapper.toDomain(saved);
        } catch (DataIntegrityViolationException e) {
            if (isUniqueViolation(e, "enrollment.uq_user_id_course_id")) {
                throw new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_ALREADY_EXISTS);
            }
            throw e;
        }
    }

    // ---------- helpers

    private boolean isUniqueViolation(Throwable e, String expectedConstraintName) {
        Throwable t = e;
        while (t != null) {
            if (t instanceof org.hibernate.exception.ConstraintViolationException cve) {
                String constraintName = cve.getConstraintName();
                return expectedConstraintName.equalsIgnoreCase(constraintName);
            }
            t = t.getCause();
        }
        return false;
    }
}
