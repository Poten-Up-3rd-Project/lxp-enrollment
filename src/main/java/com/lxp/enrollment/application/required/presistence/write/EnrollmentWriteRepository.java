package com.lxp.enrollment.application.required.presistence.write;

import com.lxp.enrollment.domain.model.Enrollment;

public interface EnrollmentWriteRepository {
    Enrollment save(Enrollment enrollment);
}
