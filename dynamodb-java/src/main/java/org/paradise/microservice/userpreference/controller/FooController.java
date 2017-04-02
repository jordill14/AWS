package org.paradise.microservice.userpreference.controller;

import org.paradise.microservice.userpreference.feature.FeatureToggle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Created by terrence on 2/4/17.
 */
@RestController
@RequestMapping("/foo")
@FeatureToggle(feature = "feature.foo")
public class FooController {

    @RequestMapping("")
    public Map hello() {

        return Collections.singletonMap("message", "hello foo!");
    }

}