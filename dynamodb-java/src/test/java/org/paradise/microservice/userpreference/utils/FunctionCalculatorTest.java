package org.paradise.microservice.userpreference.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 12/12/2016.
 */
public class FunctionCalculatorTest {

    @Test
    public void testFibonacciFunction() throws Exception {

        FunctionCalculator functionCalculator = new FunctionCalculator();

        assertEquals("Incorrect result", Long.valueOf(1),  functionCalculator.fibonacci.apply(2));
        assertEquals("Incorrect result", Long.valueOf(2),  functionCalculator.fibonacci.apply(3));
        assertEquals("Incorrect result", Long.valueOf(3),  functionCalculator.fibonacci.apply(4));
        assertEquals("Incorrect result", Long.valueOf(5),  functionCalculator.fibonacci.apply(5));
        assertEquals("Incorrect result", Long.valueOf(8),  functionCalculator.fibonacci.apply(6));
        assertEquals("Incorrect result", Long.valueOf(13), functionCalculator.fibonacci.apply(7));
        assertEquals("Incorrect result", Long.valueOf(21), functionCalculator.fibonacci.apply(8));
        assertEquals("Incorrect result", Long.valueOf(34), functionCalculator.fibonacci.apply(9));
    }

}