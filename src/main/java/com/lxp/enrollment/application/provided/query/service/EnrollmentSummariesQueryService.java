package com.lxp.enrollment.application.provided.query.service;

import com.lxp.common.domain.pagination.Page;
import com.lxp.enrollment.application.provided.mapper.EnrollmentViewMapper;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentSummariesQuery;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentSummaryQueryView;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentSummariesQueryUseCase;
import com.lxp.enrollment.application.required.presistence.read.EnrollmentReadRepository;
import com.lxp.enrollment.application.required.web.ContentClient;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EnrollmentSummariesQueryService implements EnrollmentSummariesQueryUseCase {

    private final EnrollmentReadRepository enrollmentReadRepository;
    private final EnrollmentViewMapper enrollmentViewMapper;
    private final ContentClient contentClient;

    public EnrollmentSummariesQueryService(
            EnrollmentReadRepository enrollmentReadRepository,
            EnrollmentViewMapper enrollmentViewMapper,
            ContentClient contentClient
    ) {
        this.enrollmentReadRepository = enrollmentReadRepository;
        this.enrollmentViewMapper = enrollmentViewMapper;
        this.contentClient = contentClient;
    }

    @Override
    public Page<EnrollmentSummaryQueryView> execute(EnrollmentSummariesQuery query) {

        Page<Enrollment> found
                = enrollmentReadRepository.findAllByUserId(query.userId(), query.pageRequest());

        Set<UUID> courseIds = found.content().stream()
                .map(Enrollment::courseId)
                .collect(Collectors.toSet());

        List<CourseSummary> courseSummaries = List.of();
        if (!courseIds.isEmpty()) {
            courseSummaries = contentClient.getCourseSummaries(courseIds);
        }

        Map<UUID, CourseSummary> courseSummaryMap = courseSummaries
                .stream()
                .collect(Collectors.toMap(
                        courseSummary -> UUID.fromString(courseSummary.courseId()),
                        Function.identity())
                );

        return enrollmentViewMapper.toEnrollmentSummariesQueryView(found, courseSummaryMap);
    }
}
