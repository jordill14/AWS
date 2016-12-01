package org.paradise.microservice.userpreference.exception;

/**
 * Created by terrence on 1/12/2016.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserPreferenceException extends RuntimeException {

    public UserPreferenceException(String msg, Throwable t) {
        super(msg, t);
    }

}
