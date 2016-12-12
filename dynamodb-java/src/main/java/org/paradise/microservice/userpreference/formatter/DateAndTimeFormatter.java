package org.paradise.microservice.userpreference.formatter;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Created by terrence on 13/12/2016.
 */
public final class DateAndTimeFormatter {

    public static final int THREE_DIGITS_NANO_OF_SECOND = 3;

    public static final DateTimeFormatter ISO_INSTANT_WITH_NANO_SECOND = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendInstant(THREE_DIGITS_NANO_OF_SECOND)
            .toFormatter();

    private DateAndTimeFormatter() {
    }

}
