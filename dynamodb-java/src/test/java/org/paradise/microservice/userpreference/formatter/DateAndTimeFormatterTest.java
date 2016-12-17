package org.paradise.microservice.userpreference.formatter;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 13/12/2016.
 */
public class DateAndTimeFormatterTest {

    private int newYear = 2016, newMonth = 11, newDay = 22, newHour = 10, newMinute = 26, newSecond = 59, newNanoSecond = 123;
    private int oldYear = 1981, oldMonth =  1, oldDay =  2, oldHour =  8, oldMinute =  6, oldSecond =  3, oldNanoSecond =   0;

    private String newTimestampStr = getIsoFormatDateTime(newYear, newMonth, newDay, newHour, newMinute, newSecond, newNanoSecond);
    private String oldTimestampStr = getIsoFormatDateTime(oldYear, oldMonth, oldDay, oldHour, oldMinute, oldSecond, oldNanoSecond);

    private LocalDateTime newLocalDateTime = LocalDateTime.of(newYear, newMonth, newDay, newHour, newMinute, newSecond, newNanoSecond * 1000000);
    private LocalDateTime oldLocalDateTime = LocalDateTime.of(oldYear, oldMonth, oldDay, oldHour, oldMinute, oldSecond, oldNanoSecond * 1000000);

    @Test
    public void testParsing() throws Exception {

        Arrays.asList(oldTimestampStr, newTimestampStr).stream().forEach(timestamp -> {
            ZonedDateTime zonedDateTimeIsoDateTime = ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
            ZonedDateTime zonedDateTimeCustomised =
                    ZonedDateTime.parse(timestamp, DateAndTimeFormatter.ISO_INSTANT_WITH_NANO_SECOND.withZone(ZoneId.of("Z")));

            assertEquals("Incorrect Date and Time parsing result", zonedDateTimeIsoDateTime, zonedDateTimeCustomised);
        });
    }

    @Test
    public void testFormatting() throws Exception {

        assertEquals("Incorrect Date and Time formatting result for new timestamp",
                newTimestampStr, ZonedDateTime.of(newLocalDateTime, ZoneId.of("UTC")).format(DateAndTimeFormatter.ISO_INSTANT_WITH_NANO_SECOND));

        assertEquals("Incorrect Date and Time formatting result for old timestamp",
                oldTimestampStr, ZonedDateTime.of(oldLocalDateTime, ZoneId.of("UTC")).format(DateAndTimeFormatter.ISO_INSTANT_WITH_NANO_SECOND));
    }

    private String getIsoFormatDateTime(int year, int mon, int day, int hour, int min, int sec, int nanoSec) {

        return StringUtils.leftPad(String.valueOf(year), 4, "0") + "-"
                + StringUtils.leftPad(String.valueOf(mon), 2, "0") + "-"
                + StringUtils.leftPad(String.valueOf(day), 2, "0") + "T"
                + StringUtils.leftPad(String.valueOf(hour), 2, "0") + ":"
                + StringUtils.leftPad(String.valueOf(min), 2, "0") + ":"
                + StringUtils.leftPad(String.valueOf(sec), 2, "0") + "."
                + StringUtils.leftPad(String.valueOf(nanoSec), 3, "0") + "Z";
    }

}