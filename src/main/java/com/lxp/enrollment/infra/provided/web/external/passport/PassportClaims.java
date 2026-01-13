package com.lxp.enrollment.infra.provided.web.external.passport;

import java.util.List;

public record PassportClaims(
        String userId,
        List<String> roles,
        String traceId
) {
}
