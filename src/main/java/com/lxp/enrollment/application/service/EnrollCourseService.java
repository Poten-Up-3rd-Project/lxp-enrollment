package com.lxp.enrollment.application.service;

import com.lxp.enrollment.application.port.in.EnrollCourseUseCase;
import com.lxp.enrollment.application.port.in.dto.EnrollCourseResult;
import com.lxp.enrollment.application.port.out.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnrollCourseService implements EnrollCourseUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollCourseService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public EnrollCourseResult enroll(UUID userId, UUID courseId) {
        Enrollment created = enrollmentRepository.create(Enrollment.create(userId, courseId));
        return new EnrollCourseResult(
                created.id(),
                created.enrollmentStatus(),
                created.enrolledAt()
        );
    }
}
