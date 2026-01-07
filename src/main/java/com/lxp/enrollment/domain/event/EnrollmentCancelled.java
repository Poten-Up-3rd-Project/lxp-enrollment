package com.lxp.enrollment.domain.event;

import com.lxp.common.domain.event.BaseDomainEvent;

public class EnrollmentCancelled extends BaseDomainEvent {

    private final String courseUUID;
    private final String userUUID;

    public EnrollmentCancelled(String enrollmentUUID, String courseUUID, String userUUID) {
        super(enrollmentUUID);
        this.courseUUID = courseUUID;
        this.userUUID = userUUID;
    }
}
