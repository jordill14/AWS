package org.paradise.microservice.userpreference.service.dynamodb;

public enum DynamoDBTableCreateMode {

    DROP, // drops tables on very restart
    SKIP  // creates tables only if they do not exist

}
