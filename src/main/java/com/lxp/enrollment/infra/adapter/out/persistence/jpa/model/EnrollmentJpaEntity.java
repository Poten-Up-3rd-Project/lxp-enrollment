package com.lxp.enrollment.infra.adapter.out.persistence.jpa.model;

import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "enrollment",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_user_id_course_id",
                columnNames = {"user_id", "course_id"}
        ),
        indexes = @Index(
                name = "idx_course_id",
                columnList = "course_id"
        )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EnrollmentJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID courseId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    @Column(nullable = false)
    private Instant enrolledAt;

    @Column
    private Instant activatedAt;

    @Column
    private Instant completedAt;

    @Column
    private Instant deletedAt;

    @Embedded
    private CancelDetailsJpaEmbeddable cancelDetailsJpaEmbeddable;

    private EnrollmentJpaEntity(
            UUID id,
            UUID userId,
            UUID courseId,
            EnrollmentStatus enrollmentStatus,
            Instant enrolledAt,
            Instant activatedAt,
            Instant completedAt,
            Instant deletedAt,
            CancelDetailsJpaEmbeddable cancelDetailsJpaEmbeddable
    ) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.enrollmentStatus = enrollmentStatus;
        this.enrolledAt = enrolledAt;
        this.activatedAt = activatedAt;
        this.completedAt = completedAt;
        this.deletedAt = deletedAt;
        this.cancelDetailsJpaEmbeddable = cancelDetailsJpaEmbeddable;
    }

    public static EnrollmentJpaEntity create(Enrollment enrollment) {
        return new EnrollmentJpaEntity(
                enrollment.id(),
                enrollment.userId(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                enrollment.completedAt(),
                enrollment.deletedAt(),
                CancelDetailsJpaEmbeddable.of(enrollment.cancelDetails())
        );
    }
}
