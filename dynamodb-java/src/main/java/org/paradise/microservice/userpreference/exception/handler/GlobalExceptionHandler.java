package org.paradise.microservice.userpreference.exception.handler;

import org.paradise.microservice.userpreference.exception.UserPreferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Exception Handler to handle all exceptions across app.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(UserPreferenceException.class)
    public void handleApiException(UserPreferenceException ex) {

        LOG.error("API exception thrown with message: {}", ex.getMessage());
    }

}
