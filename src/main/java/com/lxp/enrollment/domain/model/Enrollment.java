package com.lxp.enrollment.domain.model;

import com.lxp.common.domain.event.AggregateRoot;
import com.lxp.enrollment.domain.event.EnrollmentCancelled;
import com.lxp.enrollment.domain.event.EnrollmentCreated;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.enums.EnrollmentStatus;
import com.lxp.enrollment.domain.model.vo.CancelDetails;

import java.time.Instant;
import java.util.UUID;

public class Enrollment extends AggregateRoot<UUID> {

    // ---------- Fields

    private UUID id;
    private UUID userId;
    private UUID courseId;

    private EnrollmentStatus enrollmentStatus;

    private Instant enrolledAt;
    private Instant activatedAt;
    private Instant completedAt;
    private Instant deletedAt;

    private CancelDetails cancelDetails;

    // ---------- constructors

    private Enrollment(
            UUID id,
            UUID userId,
            UUID courseId,
            EnrollmentStatus enrollmentStatus,
            Instant enrolledAt,
            Instant activatedAt,
            Instant completedAt,
            Instant deletedAt,
            CancelDetails cancelDetails
    ) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.enrollmentStatus = enrollmentStatus;
        this.enrolledAt = enrolledAt;
        this.activatedAt = activatedAt;
        this.completedAt = completedAt;
        this.deletedAt = deletedAt;
        this.cancelDetails = cancelDetails;
    }

    // ---------- factories

    public static Enrollment create(UUID userId, UUID courseId) {

        if (userId == null) {
            throw new EnrollmentException(EnrollmentErrorCode.USER_ID_IS_NULL);
        }

        if (courseId == null) {
            throw new EnrollmentException(EnrollmentErrorCode.COURSE_ID_IS_NULL);
        }

        Enrollment created = new Enrollment(
                UUID.randomUUID(),
                userId,
                courseId,
                EnrollmentStatus.ENROLLED,
                Instant.now(),
                null,
                null,
                null,
                null
        );

        created.registerEvent(
                new EnrollmentCreated(created.id.toString(), courseId.toString(), userId.toString()));

        return created;
    }

    public static Enrollment reconstruct(
            UUID id,
            UUID userId,
            UUID courseId,
            EnrollmentStatus enrollmentStatus,
            Instant enrolledAt,
            Instant activatedAt,
            Instant completedAt,
            Instant deletedAt,
            CancelDetails cancelDetails
    ) {
        return new Enrollment(
                id,
                userId,
                courseId,
                enrollmentStatus,
                enrolledAt,
                activatedAt,
                completedAt,
                deletedAt,
                cancelDetails
        );
    }

    // ---------- domain logics

    public void begin() {
        activatedAt = Instant.now();
        enrollmentStatus = enrollmentStatus.toInProgress();
    }

    public void cancel(
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String cancelReasonComment
    ) {
        
        // To Do: 취소 정책 여기에 적용해야 함
        
        enrollmentStatus = enrollmentStatus.toCancelled();
        this.cancelDetails = CancelDetails.now(cancelType, cancelReasonType, cancelReasonComment);

        this.registerEvent(new EnrollmentCancelled(id.toString(), courseId.toString(), userId.toString()));
    }

    public void complete() {
        completedAt = Instant.now();
        enrollmentStatus = enrollmentStatus.toCompleted();
    }

    public void reEnroll() {
        cancelDetails = null;
        enrollmentStatus = enrollmentStatus.toEnrolled();
    }

    public void delete() {
        deletedAt = Instant.now();
    }

    // ---------- getters

    @Override
    public UUID getId() {
        return id;
    }

    public UUID id() {
        return this.id;
    }

    public UUID userId() {
        return this.userId;
    }

    public UUID courseId() {
        return this.courseId;
    }

    public Instant enrolledAt() {
        return this.enrolledAt;
    }

    public EnrollmentStatus enrollmentStatus() {
        return this.enrollmentStatus;
    }

    public CancelDetails cancelDetails() {
        return this.cancelDetails;
    }
}
