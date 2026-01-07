package com.lxp.enrollment.domain.model.enums;

import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;

public enum EnrollmentStatus {

    ENROLLED, IN_PROGRESS, COMPLETED, CANCELLED
    ;

    public EnrollmentStatus toInProgress() {
        if (this.equals(EnrollmentStatus.ENROLLED)) {
            return EnrollmentStatus.IN_PROGRESS;
        }

        throw new EnrollmentException(EnrollmentErrorCode.INVALID_STATUS_CHANGE_INTO_IN_PROGRESS);
    }

    public EnrollmentStatus toCancelled() {
        if (this.equals(EnrollmentStatus.ENROLLED)
            || this.equals(EnrollmentStatus.IN_PROGRESS)) {
            return EnrollmentStatus.CANCELLED;
        }

        throw new EnrollmentException(EnrollmentErrorCode.INVALID_STATUS_CHANGE_INTO_CANCELLED);
    }

    public EnrollmentStatus toCompleted() {
        if (this.equals(EnrollmentStatus.IN_PROGRESS)) {
            return EnrollmentStatus.COMPLETED;
        }

        throw new EnrollmentException(EnrollmentErrorCode.INVALID_STATUS_CHANGE_INTO_COMPLETED);
    }

    public EnrollmentStatus toEnrolled() {
        if (this.equals(EnrollmentStatus.CANCELLED)) {
            return EnrollmentStatus.ENROLLED;
        }

        throw new EnrollmentException(EnrollmentErrorCode.INVALID_STATUS_CHANGE_INTO_ENROLLED);
    }
}
