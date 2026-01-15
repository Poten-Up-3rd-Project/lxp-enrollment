package com.lxp.enrollment.infra.provided.web.external.mapper;

import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView.CancelDetailsOfCancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CancelDetailsOfEnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CourseSummaryView.CourseTagView;
import com.lxp.enrollment.infra.provided.web.external.response.CancelByUserSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.CancelByUserSuccessResponse.CancelDetailsOfCancelByUserSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse.CancelDetailsOfEnrollmentDetailsResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse.CourseSummaryResponse.CourseTagResponse;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentResponseMapper {

    public EnrollSuccessResponse toEnrollSuccessResponse(EnrollSuccessView view) {
        return new EnrollSuccessResponse(
                view.enrollmentId(),
                view.userId(),
                view.courseId(),
                view.enrollmentStatus().name(),
                DateTimeUtils.toKstDateTime(view.enrolledAt())
        );
    }

    public CancelByUserSuccessResponse toCancelByUserSuccessResponse(CancelByUserSuccessView view) {

        CancelDetailsOfCancelByUserSuccessView cancelDetailsView = view.cancelDetailsOfCancelByUserSuccessView();
        CancelDetailsOfCancelByUserSuccessResponse cancelDetailsResponse = new CancelDetailsOfCancelByUserSuccessResponse(
                DateTimeUtils.toKstDateTime(cancelDetailsView.cancelledAt()),
                cancelDetailsView.cancelType(),
                cancelDetailsView.cancelReasonType(),
                cancelDetailsView.reason()
        );

        return new CancelByUserSuccessResponse(
                view.id(),
                view.courseId(),
                view.status(),
                DateTimeUtils.toKstDateTime(view.enrolledAt()),
                DateTimeUtils.toKstDateTime(view.activatedAt()),
                cancelDetailsResponse
        );
    }

    public EnrollmentDetailsResponse toEnrollmentDetailsResponse(EnrollmentDetailsQueryView view) {

        CancelDetailsOfEnrollmentDetailsQueryView cancelDetailsView = view.cancelDetailsOfEnrollmentDetailsQueryView();
        CancelDetailsOfEnrollmentDetailsResponse cancelDetailsResponse = new CancelDetailsOfEnrollmentDetailsResponse(
                DateTimeUtils.toKstDateTime(cancelDetailsView.cancelledAt()),
                cancelDetailsView.cancelType(),
                cancelDetailsView.cancelReasonType(),
                cancelDetailsView.reason()
        );

        return new EnrollmentDetailsResponse(
                view.id(),
                view.courseId(),
                view.status(),
                DateTimeUtils.toKstDateTime(view.enrolledAt()),
                DateTimeUtils.toKstDateTime(view.activatedAt()),
                cancelDetailsResponse,
                view.courseSummaryView().thumbnailUrl(),
                view.courseSummaryView().totalProgress(),
                view.courseSummaryView().courseTitle(),
                view.courseSummaryView().courseDescription(),
                view.courseSummaryView().instructorName(),
                view.courseSummaryView().level(),
                view.courseSummaryView().tagsView()
                        .stream()
                        .map(this::toCourseTagDetailsResponse)
                        .toList()
        );
    }

    private CourseTagResponse toCourseTagDetailsResponse(CourseTagView view) {
        return new CourseTagResponse(
                view.category(),
                view.subCategory(),
                view.tagId(),
                view.name(),
                view.state(),
                view.color(),
                view.variant()
        );
    }
}
