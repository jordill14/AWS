package org.paradise.microservice.userpreference.converter;


import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

import java.util.Objects;

/**
 * Created by terrence on 29/11/2016.
 */
public class UserPreferencesConverter {

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

}
