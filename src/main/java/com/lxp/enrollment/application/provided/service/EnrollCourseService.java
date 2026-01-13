package com.lxp.enrollment.application.provided.service;

import com.lxp.enrollment.application.provided.dto.command.EnrollCommand;
import com.lxp.enrollment.application.provided.usecase.EnrollCourseUseCase;
import com.lxp.enrollment.application.provided.dto.result.EnrollCourseResult;
import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.application.required.web.ContentClient;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

@Service
public class EnrollCourseService implements EnrollCourseUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final ContentClient contentClient;

    public EnrollCourseService(
            EnrollmentRepository enrollmentRepository,
            ContentClient contentClient
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.contentClient = contentClient;
    }

    @Override
    public EnrollCourseResult enroll(EnrollCommand command) {

        if (contentClient.courseNotExists(command.courseId())) {
            throw new EnrollmentException(EnrollmentErrorCode.COURSE_NOT_FOUND);
        }

        Enrollment created = enrollmentRepository.save(
                Enrollment.create(command.userId(), command.courseId())
        );
        return EnrollCourseResult.of(created);
    }
}
