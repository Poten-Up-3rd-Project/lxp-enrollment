package com.lxp.enrollment.infra.adapter.required.persistence.jpa;

import com.lxp.enrollment.application.port.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import com.lxp.enrollment.infra.adapter.required.persistence.jpa.model.CancelDetailsJpaEmbeddable;
import com.lxp.enrollment.infra.adapter.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EnrollmentRepositoryJpaAdapter implements EnrollmentRepository {

    private final EnrollmentJpaRepository enrollmentJpaRepository;

    public EnrollmentRepositoryJpaAdapter(EnrollmentJpaRepository enrollmentJpaRepository) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
    }

    @Override
    public Enrollment findById(UUID id) {
        // To Do: findById 구현
        return null;
    }

    @Override
    public List<Enrollment> findAllByUserId(UUID userId) {
        // To Do: findAllByUserId 구현
        return List.of();
    }

    @Override
    public List<Enrollment> findAllByCourseId(UUID courseId) {
        // To Do: findAllByCourseId 구현
        return List.of();
    }

    @Override
    public Enrollment create(Enrollment enrollment) {

        EnrollmentJpaEntity saved = enrollmentJpaRepository.save(
                EnrollmentJpaEntity.create(enrollment)
        );
        CancelDetailsJpaEmbeddable cancelDetailsJpaEmbeddable = saved.getCancelDetailsJpaEmbeddable();

        return Enrollment.reconstruct(
                saved.getId(),
                saved.getUserId(),
                saved.getCourseId(),
                saved.getEnrollmentStatus(),
                saved.getEnrolledAt(),
                saved.getActivatedAt(),
                saved.getCompletedAt(),
                saved.getDeletedAt(),
                CancelDetails.of(
                        cancelDetailsJpaEmbeddable.getCanceledAt(),
                        cancelDetailsJpaEmbeddable.getCancelType(),
                        cancelDetailsJpaEmbeddable.getCancelReasonType(),
                        cancelDetailsJpaEmbeddable.getCancelReasonComment()
                )
        );
    }

    @Override
    public Enrollment cancel(UUID userId, UUID courseId) {
        // To Do: cancel 구현
        return null;
    }
}
