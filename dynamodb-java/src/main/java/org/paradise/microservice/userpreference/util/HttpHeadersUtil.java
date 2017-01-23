package org.paradise.microservice.userpreference.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by terrence on 20/1/17.
 */
public final class HttpHeadersUtil {

    private HttpHeadersUtil() {

    }

    public static String getHeader(HttpHeaders httpHeaders, String headerName) {

        return Optional.ofNullable(httpHeaders.getFirst(headerName))
                .map(headerValue -> headerValue.trim())
                .orElse(StringUtils.EMPTY);
    }

    public static String getCookie(HttpHeaders httpHeaders, String cookieName) {

         return Arrays.stream(httpHeaders.getFirst(HttpHeaders.COOKIE).split(";"))
                .filter(cookie -> StringUtils.startsWithIgnoreCase(cookie.trim(), cookieName))
                .findFirst()
                .map(value -> value.trim())
                .orElse(StringUtils.EMPTY);
    }

}
