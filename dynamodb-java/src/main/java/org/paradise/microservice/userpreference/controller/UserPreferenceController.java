package org.paradise.microservice.userpreference.controller;

import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.exception.UserPreferenceException;
import org.paradise.microservice.userpreference.service.UserPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.paradise.microservice.userpreference.Constants.HTTP_HEADERS_APCN;
import static org.paradise.microservice.userpreference.Constants.REQUEST_PATH_USER_PREFERENCE;
import static org.paradise.microservice.userpreference.Constants.HTTP_HEADERS_OBSSOCOOKIE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by terrence on 28/11/2016.
 */
@RestController
@RequestMapping(value = REQUEST_PATH_USER_PREFERENCE)
public class UserPreferenceController {

    private static final Logger LOG = LoggerFactory.getLogger(UserPreferenceController.class);

    private UserPreferenceService userPreferenceService;

    @Autowired
    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET,  value = {"", "/"}, produces = APPLICATION_JSON_VALUE)
    public List<UserPreferences> getUserPreferencesList(
            @RequestHeader(value = HTTP_HEADERS_APCN, required = true) String apcn) {

        return userPreferenceService.getUserPreferencesByCNumber(apcn);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET,  value = "/{type}", produces = APPLICATION_JSON_VALUE)
    public UserPreferences getUserPreferences(
            @RequestHeader(value = HTTP_HEADERS_APCN, required = true) String apcn,
            @CookieValue(name = HTTP_HEADERS_OBSSOCOOKIE, required = false) String obSSOCookie,
            @PathVariable String type) {

        return userPreferenceService.getUserPreferences(apcn, getPreferenceType(type).toString());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/{type}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserPreferences createUserPreferences(
            @RequestHeader(value = HTTP_HEADERS_APCN, required = true) String apcn,
            @CookieValue(name = HTTP_HEADERS_OBSSOCOOKIE, required = false) String obSSOCookie,
            @PathVariable String type,
            @RequestBody @Valid UserPreferences userPreferences) {

        // force to set APCN
        userPreferences.setcNumber(apcn);
        // force to set Preference Type
        userPreferences.setPreferenceType(getPreferenceType(type));

        LOG.debug("save User Preferences for APCN {}", apcn);
        userPreferenceService.createUserPreferences(userPreferences);

        // retrieve the new or updated User Preferences
        return userPreferenceService.getUserPreferences(apcn, getPreferenceType(type).toString());
    }

    private PreferenceType getPreferenceType(String type) {

        try {
            return PreferenceType.valueOf(type);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new UserPreferenceException(ex.getMessage(), ex);
        }
    }

}
