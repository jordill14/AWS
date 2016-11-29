package org.paradise.microservice.userpreference.controller;

import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.UserPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

import static org.paradise.microservice.userpreference.Constants.HTTP_HEADERS_APCN;
import static org.paradise.microservice.userpreference.Constants.REQUEST_PATH_USER_PREFERENCE;
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

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserPreferences(@RequestHeader(value = HTTP_HEADERS_APCN, required = true) String apcn) {

        if (StringUtils.isEmpty(apcn)) {
            LOG.error("Missing APCN in HTTP Headers");
        }

        UserPreferences userPreferences = userPreferenceService.getUserPreferences(apcn, PreferenceType.EBAY.toString());

        return ResponseEntity.ok(userPreferences);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserPreferences(
            @RequestHeader(value = HTTP_HEADERS_APCN, required = true) String apcn,
            @RequestBody @Valid UserPreferences userPreferences) {

        if (StringUtils.isEmpty(apcn)) {
            LOG.error("Missing APCN in HTTP Headers");
        }

        if (Objects.isNull(userPreferences)) {
            LOG.error("UserPreferences is NULL");
        }

        userPreferenceService.createUserPreferences(userPreferences);

        UserPreferences userPreferencesCreated = userPreferenceService.getUserPreferences(apcn, PreferenceType.EBAY.toString());

        return ResponseEntity.ok(userPreferencesCreated);
    }

}
