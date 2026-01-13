package com.lxp.enrollment.application.provided.command.service;

import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelDetailsView;
import com.lxp.enrollment.application.provided.command.usecase.CancelByUserUseCase;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.mapper.EnrollmentViewMapper;
import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import org.springframework.stereotype.Service;

@Service
public class CancelByUserService implements CancelByUserUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentViewMapper enrollmentViewMapper;

    public CancelByUserService(
            EnrollmentRepository enrollmentRepository,
            EnrollmentViewMapper enrollmentViewMapper
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentViewMapper = enrollmentViewMapper;
    }

    @Override
    public CancelByUserSuccessView execute(CancelByUserCommand command) {

        Enrollment target = enrollmentRepository.findByUserIdAndCourseId(command.userId(), command.courseId())
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        target.cancelByUser(command.cancelReasonType(), command.reason());
        enrollmentRepository.save(target);

        return enrollmentViewMapper.toCancelByUserSuccessView(target);
    }
}
