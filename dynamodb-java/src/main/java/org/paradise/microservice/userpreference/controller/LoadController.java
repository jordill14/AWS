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
import java.util.List;
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

    private static final int MIN_YEAR = 1970;
    private static final int MAX_YEAR = 2016;

    private static final int MAX_CNUMBER = 100;
    private static final int APBCN_BASE = 1000000;

    private static final int MOD = 3;

    private static final Logger LOG = LoggerFactory.getLogger(LoadController.class);

    private UserPreferenceService userPreferenceService;

    @Autowired
    public LoadController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUserPreferences() {

        List<UserPreferences> userPreferencesList = userPreferenceService.getAllUserPreferences();

        return ResponseEntity.ok(userPreferencesList);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/index", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUserPreferencesIndex() {

        List<UserPreferences> userPreferencesList = userPreferenceService.getAllUserPreferencesIndex();

        return ResponseEntity.ok(userPreferencesList);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserPreferences() {

        IntStream.rangeClosed(1, MAX_CNUMBER).forEach(i ->
                IntStream.rangeClosed(1, MOD).forEach(j -> {
            UserPreferences userPreferences = new UserPreferences();

            userPreferences.setcNumber(String.valueOf(i));
            userPreferences.setApbcn(String.valueOf(APBCN_BASE + j));

            switch (j % MOD) {
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

        long minSecond = (int) LocalDateTime.of(MIN_YEAR, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long maxSecond = (int) LocalDateTime.of(MAX_YEAR, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        LOG.debug("minSecond: [{}]", minSecond);
        LOG.debug("maxSecond: [{}]", maxSecond);

        long randomSecond = minSecond + random.longs(1, minSecond, maxSecond).findFirst().getAsLong();

        LOG.debug("randomSecond: [{}]", randomSecond);

        LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(randomSecond, 0, ZoneOffset.UTC);

        return randomDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
    }

}
