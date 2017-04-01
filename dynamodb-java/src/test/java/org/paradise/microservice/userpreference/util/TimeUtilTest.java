package org.paradise.microservice.userpreference.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

    @Test
    public void testDateTimeParseWithISODateTime() {

        String localDateTime = "2017-03-16T23:42:42.691";
        String utcDateTime = "2017-03-16T23:42:42.691Z";
        String systemDefaultDateTime = "2017-03-16T23:42:42.691+11:00";
        String melbourneDateTime = "2017-03-16T23:42:42.691+11:00[Australia/Melbourne]";

        LocalDateTime localLocalDateTime = LocalDateTime.parse(localDateTime, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime utcLocalDateTime = LocalDateTime.parse(utcDateTime, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime systemDefaultLocalDateTime = LocalDateTime.parse(systemDefaultDateTime, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime melbourneLocalDateTime = LocalDateTime.parse(melbourneDateTime, DateTimeFormatter.ISO_DATE_TIME);

        ZonedDateTime localZonedDateTime = ZonedDateTime.parse(localDateTime + "Z");
        ZonedDateTime utcZonedDateTime = ZonedDateTime.parse(utcDateTime);
        ZonedDateTime systemDefaultZonedDateTime = ZonedDateTime.parse(systemDefaultDateTime);
        ZonedDateTime melbourneZonedDateTime = ZonedDateTime.parse(melbourneDateTime);

        assertEquals("Incorrect conversion", localLocalDateTime, localZonedDateTime.toLocalDateTime());
        assertEquals("Incorrect conversion", utcLocalDateTime, utcZonedDateTime.toLocalDateTime());
        assertEquals("Incorrect conversion", systemDefaultLocalDateTime, systemDefaultZonedDateTime.toLocalDateTime());
        assertEquals("Incorrect conversion", melbourneLocalDateTime, melbourneZonedDateTime.toLocalDateTime());
    }

    @Test
    public void testTimeConversion() {

        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        ZonedDateTime nowZonedDateTime = ZonedDateTime.now();

        String nowLocalDateTimeISO = nowLocalDateTime.toString() + "Z";
        String nowZonedDateTimeISO = nowZonedDateTime.format(DateTimeFormatter.ISO_INSTANT);

        assertNotEquals("Incorrect conversion", nowZonedDateTimeISO, nowLocalDateTimeISO);
    }

}