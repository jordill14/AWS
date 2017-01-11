package org.paradise.microservice.userpreference.controller;

import org.paradise.microservice.userpreference.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.paradise.microservice.userpreference.Constants.REQUEST_PATH_ROOT;

/**
 * Created by terrence on 28/11/2016.
 */
@RestController
@RequestMapping(value = REQUEST_PATH_ROOT)
public class RootController {

    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    /**
     * Handle request comes either to http://localhost:8080 or to http://localhost:8080/.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = {"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void doNothing() {

        LOG.info("Request to Context Path \"" + Constants.REQUEST_PATH_ROOT + "\". So I'll do nothing.");
    }

}
