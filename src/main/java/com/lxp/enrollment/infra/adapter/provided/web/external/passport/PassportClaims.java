package com.lxp.enrollment.infra.adapter.provided.web.external.passport;

import java.util.List;

public record PassportClaims(
        String userId,
        List<String> roles,
        String traceId
) {
}
