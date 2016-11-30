package org.paradise.microservice.userpreference.service.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import org.paradise.microservice.userpreference.service.DynamoDBService;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrence on 29/11/2016.
 */
@Service
public class DynamoDBServiceImpl implements DynamoDBService {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBService.class);

    @Value("${dynamo.userpreference.table}")
    private String tableName;
    @Value("${dynamo.userpreference.index.table}")
    private String indexTableName;

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public DynamoDBServiceImpl(@Qualifier("dynamoDBMapper") DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public List<UserPreferenceTable> allUserPreferenceTable() {

        LOG.debug("Load ALL User Preferences from DynamoDB table {}", tableName);

        List<UserPreferenceTable> userPreferenceTableList = new ArrayList<>();

        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        do {
            ScanResultPage<UserPreferenceTable> scanResultPage = dynamoDBMapper.scanPage(UserPreferenceTable.class,
                    dynamoDBScanExpression, getDynamoDBMapperConfigForTable());

            userPreferenceTableList.addAll(scanResultPage.getResults());

            dynamoDBScanExpression.setExclusiveStartKey(scanResultPage.getLastEvaluatedKey());
        } while (dynamoDBScanExpression.getExclusiveStartKey() != null);

        LOG.debug("Total record(s) of table {} scanned: {}", tableName, userPreferenceTableList.size());

        return userPreferenceTableList;
    }

    @Override
    public List<UserPreferenceIndexTable> allUserPreferenceIndexTable() {

        LOG.debug("Load ALL User Preferences Index from DynamoDB index table {}", indexTableName);

        List<UserPreferenceIndexTable> userPreferenceIndexTableList = new ArrayList<>();

        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        do {
            ScanResultPage<UserPreferenceIndexTable> scanResultPage = dynamoDBMapper.scanPage(UserPreferenceIndexTable.class,
                    dynamoDBScanExpression, getDynamoDBMapperConfigForIndexTable());

            userPreferenceIndexTableList.addAll(scanResultPage.getResults());

            dynamoDBScanExpression.setExclusiveStartKey(scanResultPage.getLastEvaluatedKey());
        } while (dynamoDBScanExpression.getExclusiveStartKey() != null);

        LOG.debug("Total record(s) of index table {} scanned: {}", indexTableName, userPreferenceIndexTableList.size());

        return userPreferenceIndexTableList;
    }

    @Override
    public UserPreferenceTable load(String cNumber, String preferenceType) {

        LOG.debug("Load User Preferences with cNUmber {} with Preference Type {} from DynamoDB table {}",
                cNumber, preferenceType, tableName);

        return dynamoDBMapper.load(UserPreferenceTable.class, cNumber, preferenceType, getDynamoDBMapperConfigForTable());
    }

    @Override
    public void save(UserPreferenceTable userPreferenceTable, UserPreferenceIndexTable userPreferenceIndexTable) {

        LOG.debug("Save User Preferences with cNumber {} with Preference Type {} into DynamoDB table {} and index table {}",
                userPreferenceTable.getcNumber(), userPreferenceTable.getPreferenceType().toString(), tableName, indexTableName);

        dynamoDBMapper.save(userPreferenceTable, getDynamoDBMapperConfigForTable());
        dynamoDBMapper.save(userPreferenceIndexTable, getDynamoDBMapperConfigForIndexTable());
    }

    private  DynamoDBMapperConfig getDynamoDBMapperConfigForTable() {

        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build();
    }

    private  DynamoDBMapperConfig getDynamoDBMapperConfigForIndexTable() {

        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(indexTableName))
                .build();
    }

}
