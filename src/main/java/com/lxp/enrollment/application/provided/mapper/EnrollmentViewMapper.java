package com.lxp.enrollment.application.provided.mapper;

import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.CancelDetailsView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentViewMapper {

    public CancelByUserSuccessView toCancelByUserSuccessView(Enrollment enrollment) {
        CancelDetails cancelDetails = enrollment.cancelDetails();
        CancelDetailsView cancelDetailsView = new CancelDetailsView(
                cancelDetails.cancelledAt(),
                cancelDetails.cancelType(),
                cancelDetails.cancelReasonType(),
                cancelDetails.cancelReasonComment()
        );

        return new CancelByUserSuccessView(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                cancelDetailsView
        );
    }

    public EnrollSuccessView toEnrollSuccessView(Enrollment enrollment) {
        return new EnrollSuccessView(
                enrollment.id(),
                enrollment.userId(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt()
        );
    }

    public EnrollmentDetailsQueryView toEnrollmentDetailsQueryView(Enrollment enrollment) {

        CancelDetails cancelDetails = enrollment.cancelDetails();
        CancelDetailsView cancelDetailsView = new CancelDetailsView(
                cancelDetails.cancelledAt(),
                cancelDetails.cancelType(),
                cancelDetails.cancelReasonType(),
                cancelDetails.cancelReasonComment()
        );
        return new EnrollmentDetailsQueryView(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                cancelDetailsView
        );
    }
}
