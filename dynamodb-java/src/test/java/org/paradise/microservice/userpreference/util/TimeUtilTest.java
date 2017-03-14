package org.paradise.microservice.userpreference.util;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by terrence on 14/3/17.
 */
public class TimeUtilTest {

    @Test
    public void testZoneDateTimeToUTCOffsetInMillisecond() {

        assertEquals("Offset in Millisecond should be zero for UTC",
                Long.valueOf(0), TimeUtil.zoneDateTimeToUTCOffsetInMillisecond(ZoneId.of(ZoneOffset.UTC.getId())));

        assertNotNull("Offset in Millisecond should not be null for System Default",
                TimeUtil.zoneDateTimeToUTCOffsetInMillisecond(ZoneId.systemDefault()));

        String timeZone = "Australia/Melbourne";

        assertTrue("Offset in Millisecond should be either 39600000 or 36000000",
                TimeUtil.zoneDateTimeToUTCOffsetInMillisecond(ZoneId.of(timeZone)) == 39600000L
                        || TimeUtil.zoneDateTimeToUTCOffsetInMillisecond(ZoneId.of(timeZone)) == 36000000L);

        timeZone = "America/Los_Angeles";

        assertTrue("Offset in Millisecond should be either -25200000 or -28800000",
                TimeUtil.zoneDateTimeToUTCOffsetInMillisecond(ZoneId.of(timeZone)) == -25200000L
                        || TimeUtil.zoneDateTimeToUTCOffsetInMillisecond(ZoneId.of(timeZone)) == -28800000L);
    }

    @Test
    public void testBetweenInMillisecond() {

        ZonedDateTime now = ZonedDateTime.now();

        assertEquals("Offset in Millisecond is incorrect",
                Long.valueOf(60000), TimeUtil.betweenInMillisecond(now, now.plusMinutes(1)));
    }

}