package com.lxp.enrollment.application.service;

import com.lxp.enrollment.application.port.provided.EnrollCourseUseCase;
import com.lxp.enrollment.application.port.provided.dto.EnrollCourseResult;
import com.lxp.enrollment.application.port.required.presistence.EnrollmentRepository;
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

        // To Do: content 서비스에 api 요청 전송해서 Course 존재 여부 검증 (동기 방식으로)

        Enrollment created = enrollmentRepository.create(Enrollment.create(userId, courseId));
        return new EnrollCourseResult(
                created.id(),
                created.enrollmentStatus(),
                created.enrolledAt()
        );
    }
}
