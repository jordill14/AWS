package org.paradise.microservice.userpreference.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by terrence on 12/12/2016.
 */
public class FunctionCalculator {

    private static Map<Integer, Long> fibonacciMap = new HashMap<>();

    static {
        fibonacciMap.put(0, 0L); // fibonacci(0)
        fibonacciMap.put(1, 1L); // fibonacci(1)
    }

    public static Function<Integer, Long> fibonacci = (x) ->
            fibonacciMap.computeIfAbsent(x,
                    n -> FunctionCalculator.fibonacci.apply(n - 2) + FunctionCalculator.fibonacci.apply(n - 1));

    private static Map<Integer, Long> factorialMap = new HashMap<>();

    static {
        factorialMap.put(1, 1L); // factorial(1)
    }

    public static Function<Integer, Long> factorial = (x) ->
            factorialMap.computeIfAbsent(x,
                    n -> n * FunctionCalculator.factorial.apply(n - 1));

}
