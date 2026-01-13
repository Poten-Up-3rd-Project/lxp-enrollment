package com.lxp.enrollment.infra.required.persistence.jpa;

import com.lxp.enrollment.application.port.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import com.lxp.enrollment.infra.required.persistence.jpa.model.CancelDetailsJpaEmbeddable;
import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EnrollmentRepositoryJpaAdapter implements EnrollmentRepository {

    private final EnrollmentJpaRepository enrollmentJpaRepository;

    public EnrollmentRepositoryJpaAdapter(EnrollmentJpaRepository enrollmentJpaRepository) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
    }

    @Override
    public Optional<Enrollment> findById(UUID id) {
        Optional<EnrollmentJpaEntity> optional = enrollmentJpaRepository.findById(id);

        return optional.map(this::map);

    }

    @Override
    public Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId) {
        Optional<EnrollmentJpaEntity> optional
                = enrollmentJpaRepository.findByUserIdAndCourseId(userId, courseId);

        return optional.map(this::map);

    }

    @Override
    public List<Enrollment> findAllByUserId(UUID userId) {
        List<EnrollmentJpaEntity> enrollmentJpaEntities
                = enrollmentJpaRepository.findAllByUserId(userId);

        return enrollmentJpaEntities.stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<Enrollment> findAllByCourseId(UUID courseId) {
        List<EnrollmentJpaEntity> enrollmentJpaEntities
                = enrollmentJpaRepository.findAllByCourseId(courseId);

        return enrollmentJpaEntities.stream()
                .map(this::map)
                .toList();
    }

    @Override
    public Enrollment save(Enrollment enrollment) {

        EnrollmentJpaEntity saved = enrollmentJpaRepository.save(
                EnrollmentJpaEntity.create(enrollment)
        );

        return map(saved);
    }

    // ---------- mapper: jpa entity -> domain entity

    private Enrollment map(EnrollmentJpaEntity enrollmentJpaEntity) {

        CancelDetailsJpaEmbeddable cancelDetailsJpaEmbeddable
                = enrollmentJpaEntity.getCancelDetailsJpaEmbeddable();

        return Enrollment.reconstruct(
                enrollmentJpaEntity.getId(),
                enrollmentJpaEntity.getUserId(),
                enrollmentJpaEntity.getCourseId(),
                enrollmentJpaEntity.getEnrollmentStatus(),
                enrollmentJpaEntity.getEnrolledAt(),
                enrollmentJpaEntity.getActivatedAt(),
                enrollmentJpaEntity.getCompletedAt(),
                enrollmentJpaEntity.getDeletedAt(),
                CancelDetails.of(
                        cancelDetailsJpaEmbeddable.getCanceledAt(),
                        cancelDetailsJpaEmbeddable.getCancelType(),
                        cancelDetailsJpaEmbeddable.getCancelReasonType(),
                        cancelDetailsJpaEmbeddable.getCancelReasonComment()
                )
        );
    }
}
