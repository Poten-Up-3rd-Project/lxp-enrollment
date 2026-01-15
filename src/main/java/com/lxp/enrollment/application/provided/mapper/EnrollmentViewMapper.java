package com.lxp.enrollment.application.provided.mapper;

import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView.CancelDetailsView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CourseSummaryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CourseSummaryView.CourseTagView;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.application.required.web.dto.CourseSummary.CourseTag;
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

    public EnrollmentDetailsQueryView toEnrollmentDetailsQueryView(Enrollment enrollment, CourseSummary courseSummary) {

        CancelDetails cancelDetails = enrollment.cancelDetails();
        EnrollmentDetailsQueryView.CancelDetailsView cancelDetailsView = new EnrollmentDetailsQueryView.CancelDetailsView(
                cancelDetails.cancelledAt(),
                cancelDetails.cancelType(),
                cancelDetails.cancelReasonType(),
                cancelDetails.cancelReasonComment()
        );

        CourseSummaryView courseSummaryView = new CourseSummaryView(
                courseSummary.thumbnailUrl(),
                courseSummary.totalProgress(),
                courseSummary.courseTitle(),
                courseSummary.courseDescription(),
                courseSummary.instructorName(),
                courseSummary.level(),
                courseSummary.tags()
                        .stream()
                        .map(this::toCourseTagView)
                        .toList()
        );

        return new EnrollmentDetailsQueryView(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                cancelDetailsView,
                courseSummaryView
        );
    }

    private CourseTagView toCourseTagView(CourseTag courseTag) {
        return new CourseTagView(
                courseTag.category(),
                courseTag.subCategory(),
                courseTag.tagId(),
                courseTag.name(),
                courseTag.state(),
                courseTag.color(),
                courseTag.variant()
        );
    }
}
