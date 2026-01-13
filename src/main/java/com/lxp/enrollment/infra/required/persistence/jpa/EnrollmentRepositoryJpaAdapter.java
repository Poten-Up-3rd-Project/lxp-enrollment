package com.lxp.enrollment.infra.required.persistence.jpa;

import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.infra.required.persistence.jpa.mapper.EnrollmentEntityMapper;
import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EnrollmentRepositoryJpaAdapter implements EnrollmentRepository {

    private final EnrollmentJpaRepository enrollmentJpaRepository;
    private final EnrollmentEntityMapper enrollmentEntityMapper;

    public EnrollmentRepositoryJpaAdapter(
            EnrollmentJpaRepository enrollmentJpaRepository,
            EnrollmentEntityMapper enrollmentEntityMapper
    ) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
        this.enrollmentEntityMapper = enrollmentEntityMapper;
    }

    @Override
    public Optional<Enrollment> findById(UUID id) {
        Optional<EnrollmentJpaEntity> optional = enrollmentJpaRepository.findById(id);

        return optional.map(enrollmentEntityMapper::toDomain);

    }

    @Override
    public Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId) {
        Optional<EnrollmentJpaEntity> optional
                = enrollmentJpaRepository.findByUserIdAndCourseId(userId, courseId);

        return optional.map(enrollmentEntityMapper::toDomain);

    }

    @Override
    public List<Enrollment> findAllByUserId(UUID userId) {
        List<EnrollmentJpaEntity> enrollmentJpaEntities
                = enrollmentJpaRepository.findAllByUserId(userId);

        return enrollmentEntityMapper.toDomainList(enrollmentJpaEntities);
    }

    @Override
    public List<Enrollment> findAllByCourseId(UUID courseId) {
        List<EnrollmentJpaEntity> enrollmentJpaEntities
                = enrollmentJpaRepository.findAllByCourseId(courseId);

        return enrollmentEntityMapper.toDomainList(enrollmentJpaEntities);
    }

    @Override
    public Enrollment save(Enrollment enrollment) {

        EnrollmentJpaEntity saved = enrollmentJpaRepository.save(
                enrollmentEntityMapper.toEntity(enrollment)
        );

        return enrollmentEntityMapper.toDomain(saved);
    }
}
