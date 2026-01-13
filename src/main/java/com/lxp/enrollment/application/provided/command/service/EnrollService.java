package com.lxp.enrollment.application.provided.command.service;

import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.usecase.EnrollUseCase;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.mapper.EnrollmentViewMapper;
import com.lxp.enrollment.application.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.application.required.web.ContentClient;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

@Service
public class EnrollService implements EnrollUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final ContentClient contentClient;
    private final EnrollmentViewMapper enrollmentViewMapper;

    public EnrollService(
            EnrollmentRepository enrollmentRepository,
            ContentClient contentClient,
            EnrollmentViewMapper enrollmentViewMapper
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.contentClient = contentClient;
        this.enrollmentViewMapper = enrollmentViewMapper;
    }

    @Override
    public EnrollSuccessView execute(EnrollCommand command) {

        if (contentClient.courseNotExists(command.courseId())) {
            throw new EnrollmentException(EnrollmentErrorCode.COURSE_NOT_FOUND);
        }

        Enrollment created = enrollmentRepository.save(
                Enrollment.create(command.userId(), command.courseId())
        );


        return enrollmentViewMapper.toEnrollSuccessView(created);
    }
}
