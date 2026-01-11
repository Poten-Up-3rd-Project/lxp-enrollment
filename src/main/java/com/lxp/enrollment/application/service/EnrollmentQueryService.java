package com.lxp.enrollment.application.service;

import com.lxp.enrollment.application.port.provided.EnrollmentQueryUseCase;
import com.lxp.enrollment.application.port.provided.dto.EnrollmentQueryResult;
import com.lxp.enrollment.application.port.required.presistence.EnrollmentRepository;
import com.lxp.enrollment.domain.exception.EnrollmentErrorCode;
import com.lxp.enrollment.domain.exception.EnrollmentException;
import com.lxp.enrollment.domain.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnrollmentQueryService implements EnrollmentQueryUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentQueryService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public EnrollmentQueryResult find(UUID id) {

        Enrollment found = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentException(EnrollmentErrorCode.ENROLLMENT_NOT_FOUND));

        return EnrollmentQueryResult.of(found);
    }
}
