package com.lxp.enrollment.application.provided.mapper;

import com.lxp.common.domain.pagination.Page;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView.CancelDetailsView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CourseSummaryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView.CourseSummaryView.CourseTagView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentSummaryQueryView;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.application.required.web.dto.CourseSummary.CourseTag;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import com.lxp.enrollment.infra.required.web.exception.ApiErrorCode;
import com.lxp.enrollment.infra.required.web.exception.ApiException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class EnrollmentViewMapper {

    public CancelByUserSuccessView toCancelByUserSuccessView(Enrollment enrollment) {
        CancelDetails cancelDetails = enrollment.cancelDetails();
        CancelDetailsView cancelDetailsView = null;
        if (cancelDetails != null) {
            cancelDetailsView = new CancelDetailsView(
                    cancelDetails.cancelledAt(),
                    cancelDetails.cancelType(),
                    cancelDetails.cancelReasonType(),
                    cancelDetails.cancelReasonComment()
            );
        }

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

        EnrollmentDetailsQueryView.CancelDetailsView cancelDetailsView = null;
        if (cancelDetails != null) {
            cancelDetailsView = new EnrollmentDetailsQueryView.CancelDetailsView(
                    cancelDetails.cancelledAt(),
                    cancelDetails.cancelType(),
                    cancelDetails.cancelReasonType(),
                    cancelDetails.cancelReasonComment()
            );
        }

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



    public Page<EnrollmentSummaryQueryView> toEnrollmentSummariesQueryView(
            Page<Enrollment> enrollmentPage,
            Map<UUID, CourseSummary> courseSummaryMap
    ) {

        return enrollmentPage.map(enrollment -> {
            CourseSummary courseSummary = courseSummaryMap.get(enrollment.courseId());

            // 가드
            if (courseSummary == null) {
                throw new ApiException(
                        ApiErrorCode.REQUIRED_FIELD_IS_NOT_PROVIDED,
                        "누락된 CourseSummary 존재, courseId " + enrollment.courseId() + "에 해당하는 강좌가 누락되었습니다."
                );
            }

            return toEnrollmentSummaryQueryView(enrollment, courseSummary);
        });
    }
    private EnrollmentSummaryQueryView toEnrollmentSummaryQueryView(Enrollment enrollment, CourseSummary courseSummary) {
        return new EnrollmentSummaryQueryView(
                enrollment.id(),
                enrollment.courseId(),
                enrollment.enrollmentStatus(),
                enrollment.enrolledAt(),
                enrollment.activatedAt(),
                toCancelDetailsView(enrollment.cancelDetails()),
                toCourseSummaryView(courseSummary)
        );
    }
    private EnrollmentSummaryQueryView.CancelDetailsView toCancelDetailsView(CancelDetails cancelDetails) {
        if (cancelDetails == null) {
            return null;
        }

        return new EnrollmentSummaryQueryView.CancelDetailsView(
                cancelDetails.cancelledAt(),
                cancelDetails.cancelType(),
                cancelDetails.cancelReasonType(),
                cancelDetails.cancelReasonComment()
        );
    }
    private EnrollmentSummaryQueryView.CourseSummaryView toCourseSummaryView(CourseSummary courseSummary) {
        return new EnrollmentSummaryQueryView.CourseSummaryView(
                courseSummary.thumbnailUrl(),
                courseSummary.totalProgress(),
                courseSummary.courseTitle(),
                courseSummary.courseDescription(),
                courseSummary.instructorName(),
                courseSummary.level(),
                courseSummary.tags()
                        .stream()
                        .map(this::toCourseSummaryCourseTagView)
                        .toList()
        );
    }
    private EnrollmentSummaryQueryView.CourseSummaryView.CourseTagView toCourseSummaryCourseTagView(CourseTag courseTag) {
        return new EnrollmentSummaryQueryView.CourseSummaryView.CourseTagView(
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
