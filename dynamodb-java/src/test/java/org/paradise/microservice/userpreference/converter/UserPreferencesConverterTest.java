package org.paradise.microservice.userpreference.converter;

import org.junit.Test;
import org.paradise.microservice.userpreference.domain.PreferenceType;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 2/12/2016.
 */
public class UserPreferencesConverterTest {

    private String cNumber = "12345678";
    private String abpcn = "99999";
    private String dateTimeCreated = "2015-03-11T12:27:06.000Z";
    private String dateTimeUpdated = "2016-03-11T12:27:06.000Z";

    private Map<String, Object> preferences = new HashMap<String, Object>() { {
        put("token_metadata", Arrays.asList("date_time_created", "date_time_expired", "date_time_last_sync"));
        put("token", "AustraliaPostSecurityToken");
    } };

    @Test
    public void testToUserPreferences() throws Exception {

        UserPreferences userPreferences = UserPreferencesConverter.toUserPreferences(createUserPreferenceTable());

        assertEquals("Incorrect cNumber", cNumber, userPreferences.getcNumber());
        assertEquals("Incorrect Preference Type", PreferenceType.EBAY, userPreferences.getPreferenceType());
        assertEquals("Incorrect abpcn", abpcn, userPreferences.getApbcn());
        assertEquals("Incorrect Date Time Created", dateTimeCreated, userPreferences.getDateTimeCreated());
        assertEquals("Incorrect Date Time Updated", dateTimeUpdated, userPreferences.getDateTimeUpdated());
        assertEquals("Incorrect Preferences", preferences.toString(), userPreferences.getPreferences().toString());
    }

    private UserPreferenceTable createUserPreferenceTable() {

        UserPreferenceTable userPreferenceTable = new UserPreferenceTable();

        userPreferenceTable.setcNumber(cNumber);
        userPreferenceTable.setPreferenceType(PreferenceType.EBAY.toString());
        userPreferenceTable.setApbcn(abpcn);
        userPreferenceTable.setDateTimeCreated(dateTimeCreated);
        userPreferenceTable.setDateTimeUpdated(dateTimeUpdated);
        userPreferenceTable.setPreferences(preferences);

        return userPreferenceTable;
    }

}