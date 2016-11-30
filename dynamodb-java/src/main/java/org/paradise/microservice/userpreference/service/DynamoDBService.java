package org.paradise.microservice.userpreference.service;


import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;

/**
 * Created by terrence on 29/11/2016.
 */
public interface DynamoDBService {

    ScanResultPage<UserPreferenceTable> allUserPreferenceTable();

    ScanResultPage<UserPreferenceIndexTable> allUserPreferenceIndexTable();

    UserPreferenceTable load(String cNumber, String preferenceType);

    void save(UserPreferenceTable userPreferenceTable, UserPreferenceIndexTable userPreferenceIndexTable);

}
