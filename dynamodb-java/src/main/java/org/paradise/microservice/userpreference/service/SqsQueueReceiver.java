package org.paradise.microservice.userpreference.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
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

    @SqsListener(value = "${cloud.aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveSQSMessage(@Headers Map<String, String> headers, @NotificationMessage String message) {

        LOG.info("Message received from queue in AWS SQS: " + message);
    }

}
