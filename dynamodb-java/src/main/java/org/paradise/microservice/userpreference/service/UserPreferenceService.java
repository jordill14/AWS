package org.paradise.microservice.userpreference.service;


import org.paradise.microservice.userpreference.domain.UserPreferences;

import java.util.List;

/**
 * Created by terrence on 28/11/2016.
 */
public interface UserPreferenceService {

    UserPreferences getUserPreferences(String cNumber, String preferenceType);

    List<UserPreferences> getAllUserPreferences();

    List<UserPreferences> getAllUserPreferencesIndex();

    void createUserPreferences(UserPreferences userPreferences);

}
