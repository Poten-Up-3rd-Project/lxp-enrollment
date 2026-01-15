package com.lxp.enrollment.application.required.web;

import java.util.UUID;

public interface ContentClient {

    Boolean courseExists(UUID courseId);

    Boolean courseNotExists(UUID courseId);
}
