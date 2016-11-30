package org.paradise.microservice.userpreference.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.paradise.microservice.userpreference.service.dynamodb.DynamoDBTableCreateMode;
import org.paradise.microservice.userpreference.service.dynamodb.DynamoDBTableUtils;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceIndexTable;
import org.paradise.microservice.userpreference.service.dynamodb.UserPreferenceTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("org.paradise.microservice.userpreference.config")
public class DBContext {

    private static final Logger LOG = LoggerFactory.getLogger(DBContext.class);

    @Value("${dynamo.throughput.read.units}")
    private long readThroughput;
    @Value("${dynamo.throughput.write.units}")
    private long writeThroughput;
    @Value("${dynamo.creation.mode}")
    private DynamoDBTableCreateMode mode;
    @Value("${dynamo.creation.timeout}")
    private long tableTimeout;
    @Value("${dynamo.userpreference.table}")
    private String tableName;
    @Value("${dynamo.userpreference.index.table}")
    private String indexTableName;
    @Autowired
    private AmazonDynamoDBClient amazonDynamoDBClient;


    @Bean
    public DynamoDBMapper dynamoDBMapper() {

        return new DynamoDBMapper(amazonDynamoDBClient);
    }

    @PostConstruct
    public void initDB() {

        LOG.info("Init DynamoDB table");

        if (DynamoDBTableCreateMode.DROP == mode) {
            LOG.info("Drop DynamoDB Table {} and Index Table {} if exists", tableName, indexTableName);
            DynamoDBTableUtils.deleteTableIfExists(amazonDynamoDBClient, tableName);
            DynamoDBTableUtils.deleteTableIfExists(amazonDynamoDBClient, indexTableName);
        }

        LOG.info("Create DynamoDB Table {} and Index Table {} if NOT exists", tableName, indexTableName);
        DynamoDBTableUtils.createTableIfNotExists(
                amazonDynamoDBClient, tableName, UserPreferenceTable.class, readThroughput, writeThroughput);
        DynamoDBTableUtils.createTableIfNotExists(
                amazonDynamoDBClient, indexTableName, UserPreferenceIndexTable.class, readThroughput, writeThroughput);
    }

}
