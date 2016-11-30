package org.paradise.microservice.userpreference.service.impl;


import org.paradise.microservice.userpreference.converter.UserPreferenceTableConverter;
import org.paradise.microservice.userpreference.converter.UserPreferencesConverter;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.DynamoDBService;
import org.paradise.microservice.userpreference.service.UserPreferenceService;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by terrence on 28/11/2016.
 */
@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private static final Logger LOG = LoggerFactory.getLogger(UserPreferenceServiceImpl.class);

    private DynamoDBService dynamoDBService;

    @Autowired
    public UserPreferenceServiceImpl(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    @Override
    public UserPreferences getUserPreferences(String apcn, String preferenceType) {

        LOG.debug("Get User Preferences with APCN {} with Preference Type {}",
                apcn, preferenceType);

        UserPreferenceTable userPreferenceTable = dynamoDBService.load(apcn, preferenceType);

        return UserPreferencesConverter.toUserPreferences(userPreferenceTable);
    }

    @Override
    public void createUserPreferences(UserPreferences userPreferences) {

        // Check existing User Preferences
        UserPreferenceTable userPreferenceTable = dynamoDBService.load(userPreferences.getcNumber(),
                userPreferences.getPreferenceType().toString());

        if (Objects.isNull(userPreferenceTable)) {
            userPreferenceTable = UserPreferenceTableConverter.toUserPreferenceTable(userPreferences);
            userPreferenceTable.setDateTimeCreated(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        } else {
            String dateTimeCreated = userPreferenceTable.getDateTimeCreated();

            userPreferenceTable = UserPreferenceTableConverter.toUserPreferenceTable(userPreferences);
            userPreferenceTable.setDateTimeCreated(dateTimeCreated);
            userPreferenceTable.setDateTimeUpdated(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        }

        UserPreferenceIndexTable userPreferenceIndexTable = new UserPreferenceIndexTable();

        userPreferenceIndexTable.setcNumber(userPreferenceTable.getcNumber());
        userPreferenceIndexTable.setPreferenceType(userPreferenceTable.getPreferenceType());

        LOG.debug("Save User Preferences with cNumber {} with Preference Type {}",
                userPreferences.getcNumber(), userPreferences.getPreferenceType().toString());

        dynamoDBService.save(userPreferenceTable, userPreferenceIndexTable);
    }

}
