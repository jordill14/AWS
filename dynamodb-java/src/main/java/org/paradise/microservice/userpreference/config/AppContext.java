package org.paradise.microservice.userpreference.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
public class AppContext {

    private static final Logger LOG = LoggerFactory.getLogger(AppContext.class);

    @Value("${cloud.aws.credentials.accessKey}")
    private String awsAccessKeyId;
    @Value("${cloud.aws.credentials.secretKey}")
    private String awsSecretAccessKey;
    @Value("${cloud.aws.region.static}")
    private String amazonRegion;

    @Value("${cloud.aws.connection.timeout}")
    private int awsConnectionTimeout;
    @Value("${cloud.aws.connection.max}")
    private int awsConnectionMax;
    @Value("${cloud.aws.socket.timeout}")
    private int awsSocketTimeout;

    @Value("${dynamo.endpoint}")
    private String endpoint;

    @Autowired
    private ClientConfiguration clientConfiguration;


    @Bean
    public ClientConfiguration clientConfiguration() {

        ClientConfiguration configuration = new ClientConfiguration();

        // No Retries
        configuration.setMaxErrorRetry(0);

        configuration.setConnectionTimeout(awsConnectionTimeout);
        configuration.setMaxConnections(awsConnectionMax);
        configuration.setSocketTimeout(awsSocketTimeout);

        return configuration;
    }

    @Bean
    @Profile("local")
    public AmazonDynamoDBClient localAmazonDynamoDBClient() throws IOException {

        LOG.info("AmazonDynamoDBClient in local profile");

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(
                new StaticCredentialsProvider(new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)));

        dynamoDBClient.setRegion(Region.getRegion(Regions.fromName(amazonRegion)));
        dynamoDBClient.setEndpoint(endpoint);

        return dynamoDBClient;
    }

    @Bean
    @Profile("!local")
    public AmazonDynamoDBClient amazonDynamoDBClient() throws IOException {

        LOG.info("AmazonDynamoDBClient in non-local profile");

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(clientConfiguration);

        dynamoDBClient.setRegion(Region.getRegion(Regions.fromName(amazonRegion)));

        return dynamoDBClient;
    }

}
