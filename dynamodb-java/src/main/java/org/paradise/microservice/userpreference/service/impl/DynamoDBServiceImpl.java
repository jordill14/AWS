package org.paradise.microservice.userpreference.service.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.paradise.microservice.userpreference.service.DynamoDBService;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by terrence on 29/11/2016.
 */
@Service
public class DynamoDBServiceImpl implements DynamoDBService {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBService.class);

    @Value("${dynamo.userpreference.table}")
    private String tableName;

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public DynamoDBServiceImpl(@Qualifier("dynamoDBMapper") DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public UserPreferenceTable load(String apcn, String preferenceType) {

        LOG.debug("Load User Preferences with HashKey / APCN {} with Preference Type {} from DynamoDB table {}",
                apcn, preferenceType, tableName);

        return dynamoDBMapper.load(UserPreferenceTable.class, apcn, preferenceType, getDynamoDBMapperConfig());
    }

    @Override
    public void save(UserPreferenceTable userPreferenceTable) {

        LOG.debug("Save User Preferences with cNumber {} with Preference Type {} into DynamoDB table {}",
                userPreferenceTable.getcNumber(), userPreferenceTable.getPreferenceType().toString(), tableName);

        dynamoDBMapper.save(userPreferenceTable, getDynamoDBMapperConfig());
    }

    private  DynamoDBMapperConfig getDynamoDBMapperConfig() {

        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build();
    }
}
