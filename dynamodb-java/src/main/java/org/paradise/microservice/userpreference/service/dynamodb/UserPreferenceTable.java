package org.paradise.microservice.userpreference.service.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;

import java.util.Map;

@DynamoDBTable(tableName = "REPLACED_BY_VALUE_IN_PROPERTIES_FILE")
public class UserPreferenceTable {

    @DynamoDBHashKey
    private String cNumber;

    @DynamoDBAttribute
    private String apbcn;

    @DynamoDBRangeKey
    private String preferenceType;

    @DynamoDBAttribute
    private String dateTimeCreated;

    @DynamoDBAttribute
    private String dateTimeUpdated;

    @DynamoDBTypeConvertedJson
    private Map<String, Object> preferences;


    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getApbcn() {
        return apbcn;
    }

    public void setApbcn(String apbcn) {
        this.apbcn = apbcn;
    }

    public String getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(String dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    public Map<String, Object> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, Object> preferences) {
        this.preferences = preferences;
    }

}
