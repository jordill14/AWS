package org.paradise.microservice.userpreference.service;


import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

import java.util.List;

/**
 * Created by terrence on 29/11/2016.
 */
public interface DynamoDBService {

    List<UserPreferenceTable> allUserPreferenceTable();

    List<UserPreferenceIndexTable> allUserPreferenceIndexTable();

    List<UserPreferenceTable> query(String cNumber);

    UserPreferenceTable load(String cNumber, String preferenceType);

    void save(UserPreferenceTable userPreferenceTable, UserPreferenceIndexTable userPreferenceIndexTable);

}
