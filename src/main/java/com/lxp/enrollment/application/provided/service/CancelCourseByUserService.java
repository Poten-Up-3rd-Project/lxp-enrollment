package com.lxp.enrollment.application.provided.service;

import com.lxp.enrollment.application.provided.dto.command.CancelByUserCommand;
import com.lxp.enrollment.application.provided.usecase.CancelCourseByUserUseCase;
import com.lxp.enrollment.application.provided.dto.result.CancelCourseResult;
import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

@Service
public class CancelCourseByUserService implements CancelCourseByUserUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public CancelCourseByUserService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public CancelCourseResult cancelByUser(CancelByUserCommand cancelByUserCommand) {

        Enrollment target = enrollmentRepository.findByUserIdAndCourseId(cancelByUserCommand.userId(), cancelByUserCommand.courseId())
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        target.cancelByUser(cancelByUserCommand.cancelReasonType(), cancelByUserCommand.reason());
        enrollmentRepository.save(target);

        return CancelCourseResult.of(target);
    }
}
