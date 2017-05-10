package org.paradise.microservice.userpreference.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static com.google.common.base.Predicates.instanceOf;
import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by terrence on 10/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class JavaSlangUtilTest {

    @Mock
    JsonGenerator mockJsonGenerator;

    private String jsonGenerationExceptionMessage = "JsonGenerationException message";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMatchAndCase() throws Exception {

        Double object = new Double(1);

        Number plusOne = Match(object).of(
                Case(instanceOf(Integer.class), i -> i + 1),
                Case(instanceOf(Double.class), d -> d + 2),
                Case($(), o -> { throw new NumberFormatException(); })
        );

        assertThat("Incorrect number", plusOne, is(3.0));

        Throwable throwable = new JsonGenerationException(jsonGenerationExceptionMessage, mockJsonGenerator);

        HttpStatus httpStatus = Match(throwable).of(
                Case(instanceOf(NumberFormatException.class), HttpStatus.OK),
                Case(instanceOf(JsonProcessingException.class), HttpStatus.BAD_REQUEST),
                Case($(), HttpStatus.INTERNAL_SERVER_ERROR));

        assertThat("Incorrect exception", httpStatus, is(HttpStatus.BAD_REQUEST));
    }

}