package com.lxp.enrollment.application.required.web;

import com.lxp.enrollment.application.required.web.dto.CourseSummary;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ContentClient {

    Boolean courseExists(UUID courseId);

    Boolean courseNotExists(UUID courseId);

    CourseSummary getCourseSummary(UUID courseId);

    List<CourseSummary> getCourseSummaries(Set<UUID> courseIds);
}
