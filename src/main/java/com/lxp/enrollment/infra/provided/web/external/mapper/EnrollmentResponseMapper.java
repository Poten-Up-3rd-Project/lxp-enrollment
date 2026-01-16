package com.lxp.enrollment.infra.provided.web.external.mapper;

import com.lxp.common.domain.pagination.Page;
import com.lxp.common.util.DateTimeUtils;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView.CancelDetailsView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CourseSummaryView.CourseTagView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentSummaryQueryView;
import com.lxp.enrollment.infra.provided.web.external.response.CancelByUserSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.CancelByUserSuccessResponse.CancelDetailsResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollSuccessResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentDetailsResponse.CourseSummaryResponse.CourseTagResponse;
import com.lxp.enrollment.infra.provided.web.external.response.EnrollmentSummaryResponse;
import org.springframework.stereotype.Component;

import java.util.List;

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

        CancelDetailsView cancelDetailsView = view.cancelDetailsView();

        CancelDetailsResponse cancelDetailsResponse = null;
        if (cancelDetailsView != null) {
            cancelDetailsResponse = new CancelDetailsResponse(
                    DateTimeUtils.toKstDateTime(cancelDetailsView.cancelledAt()),
                    cancelDetailsView.cancelType(),
                    cancelDetailsView.cancelReasonType(),
                    cancelDetailsView.reason()
            );
        }

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

        EnrollmentDetailsQueryView.CancelDetailsView cancelDetailsView = view.cancelDetailsView();

        EnrollmentDetailsResponse.CancelDetailsResponse cancelDetailsResponse = null;
        if (cancelDetailsView != null) {
            cancelDetailsResponse = new EnrollmentDetailsResponse.CancelDetailsResponse(
                    DateTimeUtils.toKstDateTime(cancelDetailsView.cancelledAt()),
                    cancelDetailsView.cancelType(),
                    cancelDetailsView.cancelReasonType(),
                    cancelDetailsView.reason()
            );
        }

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



    public Page<EnrollmentSummaryResponse> toEnrollmentSummariesResponse(Page<EnrollmentSummaryQueryView> view) {

        List<EnrollmentSummaryResponse> viewResponse = view.content()
                .stream()
                .map(this::toEnrollmentSummaryResponse)
                .toList();

        return Page.of(
                viewResponse,
                view.pageNumber(),
                view.pageSize(),
                view.totalElements()
        );
    }
    private EnrollmentSummaryResponse toEnrollmentSummaryResponse(EnrollmentSummaryQueryView view) {
        return new EnrollmentSummaryResponse(
                view.id(),
                view.courseId(),
                view.status(),
                DateTimeUtils.toKstDateTime(view.enrolledAt()),
                DateTimeUtils.toKstDateTime(view.activatedAt()),
                toCancelDetailsResponse(view.cancelDetailsView()),
                view.courseSummaryView().thumbnailUrl(),
                view.courseSummaryView().totalProgress(),
                view.courseSummaryView().courseTitle(),
                view.courseSummaryView().courseDescription(),
                view.courseSummaryView().instructorName(),
                view.courseSummaryView().level(),
                view.courseSummaryView().tagsView().stream()
                        .map(this::toCourseSummaryResponse)
                        .toList()
        );
    }
    private EnrollmentSummaryResponse.CancelDetailsResponse toCancelDetailsResponse(
            EnrollmentSummaryQueryView.CancelDetailsView view
    ) {

        if (view == null) {
            return null;
        }

        return new EnrollmentSummaryResponse.CancelDetailsResponse(
                DateTimeUtils.toKstDateTime(view.cancelledAt()),
                view.cancelType(),
                view.cancelReasonType(),
                view.reason()
        );
    }
    private EnrollmentSummaryResponse.CourseSummaryResponse.CourseTagResponse toCourseSummaryResponse(
            EnrollmentSummaryQueryView.CourseSummaryView.CourseTagView view
    ) {

        return new EnrollmentSummaryResponse.CourseSummaryResponse.CourseTagResponse(
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
