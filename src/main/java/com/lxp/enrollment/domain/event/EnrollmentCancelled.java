package com.lxp.enrollment.domain.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.common.event.CrudEvent;
import lombok.Getter;

@Getter
public class EnrollmentCancelled extends BaseDomainEvent implements CrudEvent {

    private final String courseUUID;
    private final String userUUID;

    public EnrollmentCancelled(String enrollmentUUID, String courseUUID, String userUUID) {
        super(enrollmentUUID);
        this.courseUUID = courseUUID;
        this.userUUID = userUUID;
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.DELETED;
    }
}
