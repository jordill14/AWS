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
public class UserPreferenceTableConverterTest {

    private String cNumber = "12345678";
    private String abpcn = "99999";
    private String dateTimeCreated = "2015-03-11T12:27:06.000Z";
    private String dateTimeUpdated = "2016-03-11T12:27:06.000Z";

    private Map<String, Object> preferences = new HashMap<String, Object>() { {
        put("token_metadata", Arrays.asList("date_time_created", "date_time_expired", "date_time_last_sync"));
        put("token", "AustraliaPostSecurityToken");
    } };

    @Test
    public void testToUserPreferenceTable() throws Exception {

        UserPreferenceTable userPreferenceTable = UserPreferenceTableConverter.toUserPreferenceTable(createUserPreferences());

        assertEquals("Incorrect cNumber", cNumber, userPreferenceTable.getcNumber());
        assertEquals("Incorrect Preference Type", PreferenceType.EBAY.toString(), userPreferenceTable.getPreferenceType());
        assertEquals("Incorrect abpcn", abpcn, userPreferenceTable.getApbcn());
        assertEquals("Incorrect Date Time Created", dateTimeCreated, userPreferenceTable.getDateTimeCreated());
        assertEquals("Incorrect Date Time Updated", dateTimeUpdated, userPreferenceTable.getDateTimeUpdated());
        assertEquals("Incorrect Preferences", preferences.toString(), userPreferenceTable.getPreferences().toString());
    }

    private UserPreferences createUserPreferences() {

        UserPreferences userPreferences = new UserPreferences();

        userPreferences.setcNumber(cNumber);
        userPreferences.setPreferenceType(PreferenceType.EBAY);
        userPreferences.setApbcn(abpcn);
        userPreferences.setDateTimeCreated(dateTimeCreated);
        userPreferences.setDateTimeUpdated(dateTimeUpdated);
        userPreferences.setPreferences(preferences);

        return userPreferences;
    }

}