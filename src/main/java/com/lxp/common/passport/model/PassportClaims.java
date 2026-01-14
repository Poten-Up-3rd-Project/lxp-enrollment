package com.lxp.common.passport.model;

import java.util.List;

public record PassportClaims(
        String userId,
        List<String> roles,
        String traceId
) {
}
