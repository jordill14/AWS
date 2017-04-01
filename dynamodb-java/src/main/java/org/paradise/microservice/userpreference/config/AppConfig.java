package org.paradise.microservice.userpreference.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

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


    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        // serialise Java object into JSON
//        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        // deserialise JSON into Java Object
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        return objectMapper;
    }

    @Bean
    public ClientConfiguration clientConfiguration() {

        ClientConfiguration clientConfiguration = new ClientConfiguration();

        // No Retries
        clientConfiguration.setMaxErrorRetry(0);

        clientConfiguration.setConnectionTimeout(awsConnectionTimeout);
        clientConfiguration.setMaxConnections(awsConnectionMax);
        clientConfiguration.setSocketTimeout(awsSocketTimeout);

        return clientConfiguration;
    }

    @Bean
    @Profile("local")
    public AmazonDynamoDBClient localAmazonDynamoDBClient() throws IOException {

        LOG.info("AmazonDynamoDBClient in local profile");

        AmazonDynamoDBClient dynamoDBClient = (AmazonDynamoDBClient) AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, amazonRegion))
                .build();

        return dynamoDBClient;
    }

    @Bean
    @Profile("!local")
    public AmazonDynamoDBClient amazonDynamoDBClient() throws IOException {

        LOG.info("AmazonDynamoDBClient in non-local profile");

        AmazonDynamoDBClient dynamoDBClient = (AmazonDynamoDBClient) AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)))
                .withRegion(amazonRegion)
                .build();

        return dynamoDBClient;
    }

    @Bean
    public AmazonS3 amazonS3Client() {

        return AmazonS3ClientBuilder.defaultClient();
    }

}
