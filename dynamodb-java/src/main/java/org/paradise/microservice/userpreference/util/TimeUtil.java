package org.paradise.microservice.userpreference.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import static org.apache.commons.lang3.time.DateUtils.MILLIS_PER_SECOND;

/**
 * Created by terrence on 14/3/17.
 */
public final class TimeUtil {

    private TimeUtil() {

    }

    public static Long zoneDateTimeToUTCOffsetInMillisecond(ZoneId zoneId) {

        return ZonedDateTime.now(zoneId).getOffset().getTotalSeconds() * MILLIS_PER_SECOND;
    }

    public static Long betweenInMillisecond(Temporal start, Temporal end) {

        return ChronoUnit.MILLIS.between(start, end);
    }

}
