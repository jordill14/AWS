package org.paradise.microservice.userpreference.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by terrence on 18/3/17.
 */
@Component
public class SqsQueueReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(SqsQueueReceiver.class);

    @Autowired
    private ObjectMapper objectMapper;

    @SqsListener(value = "${cloud.aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveSQSMessage(@Headers Map<String, Object> headers,  UserPreferences userPreferences)
            throws JsonProcessingException {

        LOG.info("Message received from AWS SQS Header:");
        headers.forEach((key, value) -> LOG.info("{} : {}", key, value));

        LOG.info("Message received from AWS SQS Body: " + objectMapper.writeValueAsString(userPreferences));
    }

}
