package com.lxp.enrollment.domain.event;

import com.lxp.common.domain.event.BaseDomainEvent;
import com.lxp.common.event.CrudEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EnrollmentCreated extends BaseDomainEvent implements CrudEvent {
    private final String courseUUID;
    private final String userUUID;

    public EnrollmentCreated(String enrollmentUuid, String courseId, String userId) {
        super(enrollmentUuid);

        this.courseUUID = courseId;
        this.userUUID = userId;
    }

    public EnrollmentCreated(String eventId, LocalDateTime occurredAt, String enrollmentUuid, String courseId, String userId) {
        super(eventId, enrollmentUuid, occurredAt);
        this.courseUUID = courseId;
        this.userUUID = userId;
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.CREATED;
    }
}
