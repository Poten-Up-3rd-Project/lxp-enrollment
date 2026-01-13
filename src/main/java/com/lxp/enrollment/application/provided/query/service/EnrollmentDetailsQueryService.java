package com.lxp.enrollment.application.provided.query.service;

import com.lxp.enrollment.application.provided.mapper.EnrollmentViewMapper;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentDetailsQueryUseCase;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentDetailsQueryService implements EnrollmentDetailsQueryUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentViewMapper enrollmentViewMapper;

    public EnrollmentDetailsQueryService(
            EnrollmentRepository enrollmentRepository,
            EnrollmentViewMapper enrollmentViewMapper
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentViewMapper = enrollmentViewMapper;
    }

    @Override
    public EnrollmentDetailsQueryView execute(EnrollmentDetailsQuery query) {

        Enrollment found = enrollmentRepository.findByUserIdAndCourseId(query.userId(), query.courseId())
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        return enrollmentViewMapper.toEnrollmentDetailsQueryView(found);
    }
}
