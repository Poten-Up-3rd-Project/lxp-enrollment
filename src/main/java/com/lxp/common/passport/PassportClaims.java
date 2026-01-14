package com.lxp.common.passport;

import java.util.List;

public record PassportClaims(
        String userId,
        List<String> roles,
        String traceId
) {
}
