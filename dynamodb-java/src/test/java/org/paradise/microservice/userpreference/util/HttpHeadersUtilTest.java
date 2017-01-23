package org.paradise.microservice.userpreference.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.paradise.microservice.userpreference.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 20/1/17.
 */
public class HttpHeadersUtilTest {

    private String cNumber1 = "C1234567890";
    private String cNumber2 = "C0987654321";

    private String apcn1 = "1234567890";
    private String apcn2 = "0987654321";

    private String obSSOCookie1 = "obSSOCookie-1";
    private String obSSOCookie2 = "obSSOCookie-2";

    private HttpHeaders fullHttpHeaders = generateHttpHeaders();
    private HttpHeaders emptyHttpHeaders = generateHttpHeadersWithoutSpecificHeaders();


    @Test
    public void testGetHeader() throws Exception {

        assertEquals("Invalid cNumber",
                cNumber2, HttpHeadersUtil.getHeader(fullHttpHeaders, Constants.HTTP_HEADERS_CNUMBER));
    }

    @Test
    public void testGetHeaderWithoutCNumber() throws Exception {

        assertEquals("Invalid cNumber",
                StringUtils.EMPTY, HttpHeadersUtil.getHeader(emptyHttpHeaders, Constants.HTTP_HEADERS_CNUMBER));
    }

    @Test
    public void testGetCookie() throws Exception {

        assertEquals("Invalid obSSOCookie",
                Constants.HTTP_HEADERS_OBSSOCOOKIE + "=" + obSSOCookie2,
                HttpHeadersUtil.getCookie(fullHttpHeaders, Constants.HTTP_HEADERS_OBSSOCOOKIE));
    }

    @Test
    public void testGetCookieWithoutObSSOCookie() throws Exception {

        assertEquals("Invalid obSSOCookie",
                StringUtils.EMPTY,
                HttpHeadersUtil.getCookie(emptyHttpHeaders, Constants.HTTP_HEADERS_OBSSOCOOKIE));
    }

    private HttpHeaders generateHttpHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

        httpHeaders.set(Constants.HTTP_HEADERS_CNUMBER, cNumber1);
        httpHeaders.set(Constants.HTTP_HEADERS_CNUMBER, cNumber2);

        httpHeaders.set("Rubbish Header", "Rubbish");

        httpHeaders.set(HttpHeaders.COOKIE,
                Constants.HTTP_HEADERS_CSSO_WIDGET_COOKIE_APCN + "=" + apcn2 + "; "
                        + "Rubbish Cookie= Rubbish Cookie 1; "
                        + Constants.HTTP_HEADERS_OBSSOCOOKIE + "=" + obSSOCookie2 + "; "
                        + "Junk Cookie= Junk Cookie 1; "
                        + Constants.HTTP_HEADERS_OBSSOCOOKIE + "=" + obSSOCookie1 + ";"
                        + Constants.HTTP_HEADERS_CSSO_WIDGET_COOKIE_APCN + "=" + apcn1 + "; ");

        httpHeaders.set("Junk Header", "Junk");

        return httpHeaders;
    }

    private HttpHeaders generateHttpHeadersWithoutSpecificHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

        httpHeaders.set("Rubbish Header", "Rubbish");

        httpHeaders.set(HttpHeaders.COOKIE,
                Constants.HTTP_HEADERS_CSSO_WIDGET_COOKIE_APCN + "=" + apcn2 + "; "
                        + "Rubbish Cookie= Rubbish Cookie 1; "
                        + Constants.HTTP_HEADERS_CSSO_WIDGET_COOKIE_APCN + "=" + apcn1 + "; "
                        + "Junk Cookie= Junk Cookie 1; ");

        httpHeaders.set("Junk Header", "Junk");

        return httpHeaders;
    }

}