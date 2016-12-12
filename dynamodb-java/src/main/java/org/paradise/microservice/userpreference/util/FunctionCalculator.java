package org.paradise.microservice.userpreference.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by terrence on 12/12/2016.
 */
public final class FunctionCalculator {

    public static final Map<Integer, Long> FIBONACCI_MAP = new HashMap<>();

    public static final Function<Integer, Long> FIBONACCI = (x) ->
            FIBONACCI_MAP.computeIfAbsent(x,
                    n -> FunctionCalculator.FIBONACCI.apply(n - 2) + FunctionCalculator.FIBONACCI.apply(n - 1));

    public static final Map<Integer, Long> FACTORIAL_MAP = new HashMap<>();

    public static final Function<Integer, Long> FACTORIAL = (x) ->
            FACTORIAL_MAP.computeIfAbsent(x,
                    n -> n * FunctionCalculator.FACTORIAL.apply(n - 1));

    static {
        FIBONACCI_MAP.put(0, 0L); // FIBONACCI(0)
        FIBONACCI_MAP.put(1, 1L); // FIBONACCI(1)
    }

    static {
        FACTORIAL_MAP.put(1, 1L); // FACTORIAL(1)
    }

    private FunctionCalculator() {
    }

}
