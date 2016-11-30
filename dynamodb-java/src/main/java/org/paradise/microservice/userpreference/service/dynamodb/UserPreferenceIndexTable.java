package org.paradise.microservice.userpreference.service.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "REPLACED_BY_VALUE_IN_PROPERTIES_FILE")
public class UserPreferenceIndexTable {

    @DynamoDBHashKey
    private String cNumber;

    @DynamoDBRangeKey
    private String preferenceType;

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType;
    }

}
