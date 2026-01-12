package com.lxp.enrollment.domain.model.vo;

import com.lxp.common.domain.model.ValueObject;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;

import java.time.Instant;

public class CancelDetails extends ValueObject {

    // ---------- fields

    private Instant canceledAt;
    private CancelType cancelType;
    private CancelReasonType cancelReasonType;
    private String cancelReasonComment;

    // ---------- constructors

    private CancelDetails(
            Instant canceledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String cancelReasonComment
    ) {
        this.canceledAt = canceledAt;
        this.cancelType = cancelType;
        this.cancelReasonType = cancelReasonType;
        this.cancelReasonComment = cancelReasonComment;
    }

    // ---------- factories

    public static CancelDetails of(
            Instant canceledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String cancelReasonComment
    ) {
        validate(canceledAt, cancelType, cancelReasonType, cancelReasonComment);
        return new CancelDetails(canceledAt, cancelType, cancelReasonType, cancelReasonComment);
    }

    public static CancelDetails now(
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String cancelReasonComment
    ) {
        validate(Instant.now(), cancelType, cancelReasonType, cancelReasonComment);
        return new CancelDetails(Instant.now(), cancelType, cancelReasonType, cancelReasonComment);
    }

    // ---------- getters

    public Instant cancelledAt() {
        return canceledAt;
    }

    public CancelType cancelType() {
        return cancelType;
    }

    public CancelReasonType cancelReasonType() {
        return cancelReasonType;
    }

    public String cancelReasonComment() {
        return cancelReasonComment;
    }

    // ---------- overrides

    @Override
    protected Object[] getEqualityComponents() {
        return new Object[]{
                canceledAt,
                cancelType,
                cancelReasonType,
                cancelReasonComment
        };
    }

    // ---------- helpers

    private static void validate(Instant canceledAt, CancelType cancelType, CancelReasonType cancelReasonType, String cancelReasonComment) {
        if (canceledAt == null) {
            throw new EnrollmentException(EnrollmentErrorCode.CANCELLED_AT_IS_NULL);
        }

        if (cancelType == null) {
            throw new EnrollmentException(EnrollmentErrorCode.CANCEL_TYPE_IS_NULL);
        }

        if (cancelReasonType == null) {
            throw new EnrollmentException(EnrollmentErrorCode.CANCEL_REASON_TYPE_IS_NULL);
        }

        if (cancelReasonComment == null) {
            cancelReasonComment = "";
        }
    }
}
