package org.paradise.microservice.userpreference.service.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DynamoDatabaseTableUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDatabaseTableUtils.class);

    private static DynamoDBMapper dynamoDBMapper = null;

    private DynamoDatabaseTableUtils() {

    }


    public static void createTableIfNotExists(AmazonDynamoDBClient amazonDynamoDBClient, String tableName, Class clazz,
                                              long readThroughput, long writeThroughput) {

        try {
            ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
                    .withReadCapacityUnits(readThroughput)
                    .withWriteCapacityUnits(writeThroughput);

            CreateTableRequest createTableRequest = getDynamoDBMapper(amazonDynamoDBClient)
                    .generateCreateTableRequest(clazz)
                    .withTableName(tableName)
                    .withProvisionedThroughput(provisionedThroughput);

            // Create table if it does not exist yet
            TableUtils.createTableIfNotExists(amazonDynamoDBClient, createTableRequest);
            // wait for the table to move into ACTIVE state
            TableUtils.waitUntilActive(amazonDynamoDBClient, tableName);
        } catch (AmazonServiceException ase) {
            LOG.error("AmazonServiceException caught, request made to AWS was rejected with an error response");
            LOG.error("Error Message:    " + ase.getMessage());
            LOG.error("HTTP Status Code: " + ase.getStatusCode());
            LOG.error("AWS Error Code:   " + ase.getErrorCode());
            LOG.error("Error Type:       " + ase.getErrorType());
            LOG.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            LOG.error("AmazonClientException caught, AWS client encountered internal problem while trying to communicate with AWS");
            LOG.error("Error Message: " + ace.getMessage());
        } catch (InterruptedException e) {
            LOG.warn("Interruption raised while table is creating");
        }
    }

    public static void deleteTableIfExists(AmazonDynamoDBClient amazonDynamoDBClient, String tableName) {

        DeleteTableRequest deleteTableRequest = new DeleteTableRequest().withTableName(tableName);

        TableUtils.deleteTableIfExists(amazonDynamoDBClient, deleteTableRequest);

        try {
            new DynamoDB(amazonDynamoDBClient).getTable(tableName).waitForDelete();
        } catch (InterruptedException e) {
            LOG.warn("Interruption raised while table is deleting");
        }
    }

    private static DynamoDBMapper getDynamoDBMapper(AmazonDynamoDBClient amazonDynamoDBClient) {

        if (dynamoDBMapper == null) {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);
        }

        return dynamoDBMapper;
    }

}
