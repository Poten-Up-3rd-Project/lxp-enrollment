package com.lxp.common.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

    public static OffsetDateTime toKstDateTime(Instant at) {
        return at.atZone(ZoneId.of("Asia/Seoul")).toOffsetDateTime();
    }
}
