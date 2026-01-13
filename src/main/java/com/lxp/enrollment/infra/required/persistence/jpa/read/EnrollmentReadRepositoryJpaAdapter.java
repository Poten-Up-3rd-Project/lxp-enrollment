package com.lxp.enrollment.infra.required.persistence.jpa.read;

import com.lxp.enrollment.application.required.presistence.read.EnrollmentReadRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.infra.required.persistence.jpa.mapper.EnrollmentEntityMapper;
import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EnrollmentReadRepositoryJpaAdapter implements EnrollmentReadRepository {

    private final EnrollmentReadJpaRepository enrollmentReadJpaRepository;
    private final EnrollmentEntityMapper enrollmentEntityMapper;

    public EnrollmentReadRepositoryJpaAdapter(
            EnrollmentReadJpaRepository enrollmentReadJpaRepository,
            EnrollmentEntityMapper enrollmentEntityMapper
    ) {
        this.enrollmentReadJpaRepository = enrollmentReadJpaRepository;
        this.enrollmentEntityMapper = enrollmentEntityMapper;
    }

    @Override
    public Optional<Enrollment> findById(UUID id) {
        Optional<EnrollmentJpaEntity> optional = enrollmentReadJpaRepository.findById(id);

        return optional.map(enrollmentEntityMapper::toDomain);

    }

    @Override
    public Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId) {
        Optional<EnrollmentJpaEntity> optional
                = enrollmentReadJpaRepository.findByUserIdAndCourseId(userId, courseId);

        return optional.map(enrollmentEntityMapper::toDomain);

    }

    @Override
    public List<Enrollment> findAllByUserId(UUID userId) {
        List<EnrollmentJpaEntity> enrollmentJpaEntities
                = enrollmentReadJpaRepository.findAllByUserId(userId);

        return enrollmentEntityMapper.toDomainList(enrollmentJpaEntities);
    }

    @Override
    public List<Enrollment> findAllByCourseId(UUID courseId) {
        List<EnrollmentJpaEntity> enrollmentJpaEntities
                = enrollmentReadJpaRepository.findAllByCourseId(courseId);

        return enrollmentEntityMapper.toDomainList(enrollmentJpaEntities);
    }
}
