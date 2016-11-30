package org.paradise.microservice.userpreference.controller;

import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.UserPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.paradise.microservice.userpreference.Constants.REQUEST_PATH_USER_PREFERENCE_LOAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by terrence on 28/11/2016.
 */
@RestController
@RequestMapping(value = REQUEST_PATH_USER_PREFERENCE_LOAD)
public class LoadController {

    private static final Logger LOG = LoggerFactory.getLogger(LoadController.class);

    private UserPreferenceService userPreferenceService;

    @Autowired
    public LoadController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserPreferences() {

        IntStream.rangeClosed(1, 100).forEach(i ->
                IntStream.rangeClosed(1, 3).forEach(j -> {
            UserPreferences userPreferences = new UserPreferences();

            userPreferences.setcNumber(String.valueOf(i));
            userPreferences.setApbcn(String.valueOf(1000000 + j));

            switch (j % 3) {
                case 0:
                    userPreferences.setPreferenceType(PreferenceType.ACCOUNT);
                    break;
                case 1:
                    userPreferences.setPreferenceType(PreferenceType.EBAY);
                    break;
                case 2:
                    userPreferences.setPreferenceType(PreferenceType.SENDING);
                    break;
                default:
                    break;
            }

            userPreferences.setDateTimeCreated(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));

            Map<String, String> tokenMetadata = new HashMap<>();
            tokenMetadata.put("token_date_time_created", generateRandomDateTime());
            tokenMetadata.put("token_date_time_updated", ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
            tokenMetadata.put("token_date_time_since_last_sync", generateRandomDateTime());

            Map<String, Object> preferences = new LinkedHashMap<>();
            preferences.put("token", UUID.randomUUID());
            preferences.put("token_metadata", tokenMetadata);

            userPreferences.setPreferences(preferences);

            userPreferenceService.createUserPreferences(userPreferences);
        }));

        LOG.info("ALL random data have been loaded into DynamoDB");

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    private String generateRandomDateTime() {

        Random random = new Random();

        long minSecond = (int) LocalDateTime.of(1970, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long maxSecond = (int) LocalDateTime.of(2016, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        LOG.debug("minSecond: [{}]", minSecond);
        LOG.debug("maxSecond: [{}]", maxSecond);

        long randomSecond = minSecond + random.longs(1, minSecond, maxSecond).findFirst().getAsLong();

        LOG.debug("randomSecond: [{}]", randomSecond);

        LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(randomSecond, 0, ZoneOffset.UTC);

        return randomDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
    }

}
