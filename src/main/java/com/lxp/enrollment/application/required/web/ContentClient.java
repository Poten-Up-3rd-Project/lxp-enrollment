package com.lxp.enrollment.application.required.web;

import java.util.UUID;

public interface ContentClient {

    boolean courseNotExists(UUID courseId);
}
