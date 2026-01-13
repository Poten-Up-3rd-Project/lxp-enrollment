package com.lxp.enrollment.application.provided.command.service;

import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.usecase.CancelCourseByUserUseCase;
import com.lxp.enrollment.application.provided.command.dto.view.CancelCourseView;
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
    public CancelCourseView execute(CancelByUserCommand command) {

        Enrollment target = enrollmentRepository.findByUserIdAndCourseId(command.userId(), command.courseId())
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        target.cancelByUser(command.cancelReasonType(), command.reason());
        enrollmentRepository.save(target);

        return CancelCourseView.of(target);
    }
}
