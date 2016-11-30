package org.paradise.microservice.userpreference.converter;


import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by terrence on 29/11/2016.
 */
public final class UserPreferencesConverter {

    private UserPreferencesConverter() {
    }

    public static UserPreferences toUserPreferences(UserPreferenceTable userPreferenceTable) {

        UserPreferences userPreferences = new UserPreferences();

        if (Objects.nonNull(userPreferenceTable)) {
            userPreferences.setcNumber(userPreferenceTable.getcNumber());
            userPreferences.setPreferenceType(PreferenceType.valueOf(userPreferenceTable.getPreferenceType()));
            userPreferences.setApbcn(userPreferenceTable.getApbcn());
            userPreferences.setDateTimeCreated(userPreferenceTable.getDateTimeCreated());
            userPreferences.setDateTimeUpdated(userPreferenceTable.getDateTimeUpdated());
            userPreferences.setPreferences(userPreferenceTable.getPreferences());
        }

        return userPreferences;
    }

    public static UserPreferences toUserPreferences(UserPreferenceIndexTable userPreferenceIndexTable) {

        UserPreferences userPreferences = new UserPreferences();

        if (Objects.nonNull(userPreferenceIndexTable)) {
            userPreferences.setcNumber(userPreferenceIndexTable.getcNumber());
            userPreferences.setPreferenceType(PreferenceType.valueOf(userPreferenceIndexTable.getPreferenceType()));
        }

        return userPreferences;
    }

    public static List<UserPreferences> toUserPreferencesList(List<UserPreferenceTable> userPreferenceTableList) {

        return userPreferenceTableList.stream()
                .parallel()
                .map(UserPreferencesConverter::toUserPreferences)
                .collect(Collectors.toList());
    }

    public static List<UserPreferences> toUserPreferencesIndexList(List<UserPreferenceIndexTable> userPreferenceIndexTableList) {

        return userPreferenceIndexTableList.stream()
                .parallel()
                .map(UserPreferencesConverter::toUserPreferences)
                .collect(Collectors.toList());
    }

}
