package org.paradise.microservice.userpreference.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by terrence on 12/12/2016.
 */
public class FunctionCalculatorTest {

    public static final Logger LOG = LoggerFactory.getLogger(FunctionCalculatorTest.class);

    @Test
    public void testFibonacciFunction() throws Exception {

        assertEquals("Incorrect result", Long.valueOf(0), FunctionCalculator.FIBONACCI.apply(0));
        assertEquals("Incorrect result", Long.valueOf(1), FunctionCalculator.FIBONACCI.apply(1));
        assertEquals("Incorrect result", Long.valueOf(1), FunctionCalculator.FIBONACCI.apply(2));
        assertEquals("Incorrect result", Long.valueOf(2), FunctionCalculator.FIBONACCI.apply(3));
        assertEquals("Incorrect result", Long.valueOf(3), FunctionCalculator.FIBONACCI.apply(4));
        assertEquals("Incorrect result", Long.valueOf(5), FunctionCalculator.FIBONACCI.apply(5));
        assertEquals("Incorrect result", Long.valueOf(8), FunctionCalculator.FIBONACCI.apply(6));

        assertEquals("Incorrect result", Long.valueOf(13), FunctionCalculator.FIBONACCI.apply(7));
        assertEquals("Incorrect result", Long.valueOf(21), FunctionCalculator.FIBONACCI.apply(8));
        assertEquals("Incorrect result", Long.valueOf(34), FunctionCalculator.FIBONACCI.apply(9));
        assertEquals("Incorrect result", Long.valueOf(55), FunctionCalculator.FIBONACCI.apply(10));

        assertEquals("Incorrect result", Long.valueOf(12586269025L), FunctionCalculator.FIBONACCI.apply(50));
    }

    @Test
    public void testFactorialFunction() throws Exception {

        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(1), is(Long.valueOf(1)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(2), is(Long.valueOf(2)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(3), is(Long.valueOf(6)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(4), is(Long.valueOf(24)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(5), is(Long.valueOf(120)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(6), is(Long.valueOf(720)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(7), is(Long.valueOf(5040)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(8), is(Long.valueOf(40320)));
        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(9), is(Long.valueOf(362880)));

        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(10), is(Long.valueOf(3628800)));

        assertThat("Incorrect result", FunctionCalculator.FACTORIAL.apply(20), is(Long.valueOf(2432902008176640000L)));
    }

    @Test
    public void testFFunction() throws Exception {

        assertThat("Incorrect result", FunctionCalculator.F.apply(1), is(Long.valueOf(0)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(2), is(Long.valueOf(1)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(3), is(Long.valueOf(2)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(4), is(Long.valueOf(9)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(5), is(Long.valueOf(44)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(6), is(Long.valueOf(265)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(7), is(Long.valueOf(1854)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(8), is(Long.valueOf(14833)));
        assertThat("Incorrect result", FunctionCalculator.F.apply(9), is(Long.valueOf(133496)));

        assertThat("Incorrect result", FunctionCalculator.F.apply(10), is(Long.valueOf(1334961)));

        assertThat("Incorrect result", FunctionCalculator.F.apply(20), is(Long.valueOf(895014631192902121L)));

        String outputFormat = "%20s lose in total %20s ratio %1.17f / %1.17f for f(%d)";

        IntStream.rangeClosed(1, 20)
                .forEach(i -> {
                    Long lose = FunctionCalculator.F.apply(i);

                    List<Integer> integerList = new ArrayList<>();
                    IntStream.rangeClosed(1, i).forEach(j -> integerList.add(j));

                    Long total = FunctionCalculator.FACTORIAL.apply(i);

                    LOG.info(String.format(outputFormat, lose, total, 1.0 * lose / total, (1 - lose.doubleValue() / total.doubleValue()), i));
                });

        LOG.info(String.format("Taylor Series for e (Euler's number): 1 - 1 / e = %1.17f", 1 - 1.0 / Math.E));
    }

}