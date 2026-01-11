package com.lxp.enrollment.application.service;

import com.lxp.enrollment.application.port.provided.EnrollCourseUseCase;
import com.lxp.enrollment.application.port.provided.dto.EnrollCourseResult;
import com.lxp.enrollment.application.port.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.application.port.required.web.ContentClient;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public EnrollCourseResult enroll(UUID userId, UUID courseId) {

        if (contentClient.courseNotExists(courseId)) {
            throw new EnrollmentException(EnrollmentErrorCode.COURSE_NOT_FOUND);
        }

        Enrollment created = enrollmentRepository.save(Enrollment.create(userId, courseId));
        return EnrollCourseResult.of(created);
    }
}
