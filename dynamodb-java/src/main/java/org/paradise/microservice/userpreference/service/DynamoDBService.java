package org.paradise.microservice.userpreference.service;


import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

/**
 * Created by terrence on 29/11/2016.
 */
public interface DynamoDBService {

    UserPreferenceTable load(String cNumber, String preferenceType);

    void save(UserPreferenceTable userPreferenceTable, UserPreferenceIndexTable userPreferenceIndexTable);

}
