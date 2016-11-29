package org.paradise.microservice.userpreference.converter;


import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

/**
 * Created by terrence on 29/11/2016.
 */
public class UserPreferenceTableConverter {

    public static UserPreferenceTable toUserPreferenceTable(UserPreferences userPreferences) {

        UserPreferenceTable userPreferenceTable = new UserPreferenceTable();

        userPreferenceTable.setcNumber(userPreferences.getcNumber());
        userPreferenceTable.setApbcn(userPreferences.getApbcn());
        userPreferenceTable.setPreferenceType(userPreferences.getPreferenceType().toString());
        userPreferenceTable.setDateTimeCreated(userPreferences.getDateTimeCreated());
        userPreferenceTable.setDateTimeUpdated(userPreferences.getDateTimeUpdated());
        userPreferenceTable.setPreferences(userPreferences.getPreferences());

        return userPreferenceTable;
    }

}
