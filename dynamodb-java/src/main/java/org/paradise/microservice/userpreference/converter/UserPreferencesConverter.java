package org.paradise.microservice.userpreference.converter;


import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static List<UserPreferences> toUserPreferencesList(ScanResultPage<UserPreferenceTable> scanResultPage) {

        List<UserPreferences> userPreferencesList = new ArrayList<>();

        scanResultPage.getResults().forEach(userPreferenceTable -> userPreferencesList.add(toUserPreferences(userPreferenceTable)));

        return userPreferencesList;
    }

    public static List<UserPreferences> toUserPreferencesIndexList(ScanResultPage<UserPreferenceIndexTable> scanResultPage) {

        List<UserPreferences> userPreferencesList = new ArrayList<>();

        scanResultPage.getResults().forEach(userPreferenceIndexTable -> userPreferencesList.add(toUserPreferences(userPreferenceIndexTable)));

        return userPreferencesList;
    }

}
