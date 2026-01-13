package com.lxp.enrollment.application.provided.command.service;

import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.usecase.CancelByUserUseCase;
import com.lxp.enrollment.application.provided.mapper.EnrollmentViewMapper;
import com.lxp.enrollment.application.required.presistence.read.EnrollmentReadRepository;
import com.lxp.enrollment.application.required.presistence.write.EnrollmentWriteRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

@Service
public class CancelByUserService implements CancelByUserUseCase {

    private final EnrollmentReadRepository enrollmentReadRepository;
    private final EnrollmentWriteRepository enrollmentWriteRepository;
    private final EnrollmentViewMapper enrollmentViewMapper;

    public CancelByUserService(
            EnrollmentReadRepository enrollmentReadRepository,
            EnrollmentWriteRepository enrollmentWriteRepository,
            EnrollmentViewMapper enrollmentViewMapper
    ) {
        this.enrollmentReadRepository = enrollmentReadRepository;
        this.enrollmentWriteRepository = enrollmentWriteRepository;
        this.enrollmentViewMapper = enrollmentViewMapper;
    }

    @Override
    public CancelByUserSuccessView execute(CancelByUserCommand command) {

        Enrollment target = enrollmentReadRepository.findByUserIdAndCourseId(command.userId(), command.courseId())
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        target.cancelByUser(command.cancelReasonType(), command.reason());
        enrollmentWriteRepository.save(target);

        return enrollmentViewMapper.toCancelByUserSuccessView(target);
    }
}
