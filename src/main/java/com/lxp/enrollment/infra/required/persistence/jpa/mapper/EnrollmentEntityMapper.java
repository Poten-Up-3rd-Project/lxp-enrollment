package com.lxp.enrollment.infra.required.persistence.jpa.mapper;

import com.lxp.common.application.port.out.DomainMapper;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import com.lxp.enrollment.infra.required.persistence.jpa.model.CancelDetailsJpaEmbeddable;
import com.lxp.enrollment.infra.required.persistence.jpa.model.EnrollmentJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnrollmentEntityMapper implements DomainMapper<Enrollment, EnrollmentJpaEntity> {

    @Override
    public Enrollment toDomain(EnrollmentJpaEntity entity) {

        CancelDetailsJpaEmbeddable cancelDetailsJpaEmbeddable = entity.getCancelDetailsJpaEmbeddable();

        CancelDetails cancelDetails = null;
        if (cancelDetailsJpaEmbeddable == null) {
            cancelDetails = CancelDetails.of(
                    cancelDetailsJpaEmbeddable.getCanceledAt(),
                    cancelDetailsJpaEmbeddable.getCancelType(),
                    cancelDetailsJpaEmbeddable.getCancelReasonType(),
                    cancelDetailsJpaEmbeddable.getCancelReasonComment()
            );
        }

        return Enrollment.reconstruct(
                entity.getId(),
                entity.getUserId(),
                entity.getCourseId(),
                entity.getEnrollmentStatus(),
                entity.getEnrolledAt(),
                entity.getActivatedAt(),
                entity.getCompletedAt(),
                entity.getDeletedAt(),
                cancelDetails
        );
    }

    @Override
    public List<Enrollment> toDomainList(List<EnrollmentJpaEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentJpaEntity toEntity(Enrollment domain) {

        CancelDetails cancelDetails = domain.cancelDetails();

        CancelDetailsJpaEmbeddable cancelDetailsJpaEmbeddable = null;
        if (cancelDetails == null) {
            cancelDetailsJpaEmbeddable = CancelDetailsJpaEmbeddable.of(
                    cancelDetails.cancelledAt(),
                    cancelDetails.cancelType(),
                    cancelDetails.cancelReasonType(),
                    cancelDetails.cancelReasonComment()
            );
        }

        return EnrollmentJpaEntity.create(
                domain.id(),
                domain.userId(),
                domain.courseId(),
                domain.enrollmentStatus(),
                domain.enrolledAt(),
                domain.activatedAt(),
                domain.completedAt(),
                domain.deletedAt(),
                cancelDetailsJpaEmbeddable
        );
    }

    @Override
    public List<EnrollmentJpaEntity> toEntityList(List<Enrollment> domains) {
        return domains.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
