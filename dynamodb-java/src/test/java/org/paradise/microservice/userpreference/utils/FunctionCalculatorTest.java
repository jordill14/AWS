package org.paradise.microservice.userpreference.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 12/12/2016.
 */
public class FunctionCalculatorTest {

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

        assertEquals("Incorrect result", Long.valueOf(1), FunctionCalculator.FACTORIAL.apply(1));
        assertEquals("Incorrect result", Long.valueOf(2), FunctionCalculator.FACTORIAL.apply(2));

        assertEquals("Incorrect result", Long.valueOf(3628800), FunctionCalculator.FACTORIAL.apply(10));
    }

}