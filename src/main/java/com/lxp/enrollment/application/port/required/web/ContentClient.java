package com.lxp.enrollment.application.port.required.web;

import java.util.UUID;

public interface ContentClient {

    boolean courseNotExists(UUID courseId);
}
