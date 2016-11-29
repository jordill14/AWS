package org.paradise.microservice.userpreference.service;


import org.paradise.microservice.userpreference.domain.UserPreferences;

/**
 * Created by terrence on 28/11/2016.
 */
public interface UserPreferenceService {

    UserPreferences getUserPreferences(String apcn, String preferenceType);

    void createUserPreferences(UserPreferences userPreferences);

}
