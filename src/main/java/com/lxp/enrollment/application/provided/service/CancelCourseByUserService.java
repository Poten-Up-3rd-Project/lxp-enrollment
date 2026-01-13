package com.lxp.enrollment.application.provided.service;

import com.lxp.enrollment.application.provided.CancelCourseByUserUseCase;
import com.lxp.enrollment.application.provided.dto.result.CancelCourseResult;
import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelCourseByUserService implements CancelCourseByUserUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public CancelCourseByUserService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public CancelCourseResult cancelByUser(UUID userId, UUID courseId, CancelReasonType cancelReasonType, String reason) {

        Enrollment target = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        target.cancelByUser(cancelReasonType, reason);
        enrollmentRepository.save(target);

        return CancelCourseResult.of(target);
    }
}
