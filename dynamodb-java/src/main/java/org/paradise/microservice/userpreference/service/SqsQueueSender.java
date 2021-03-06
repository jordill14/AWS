package org.paradise.microservice.userpreference.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by terrence on 18/3/17.
 */
@Component
public class SqsQueueSender {

    private static final Logger LOG = LoggerFactory.getLogger(SqsQueueSender.class);

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.sqs.queue.name}")
    private String sqsQueueName;

    @Autowired
    public SqsQueueSender(AmazonSQSAsync amazonSQSAsync) {

        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void send(String message) {

        this.queueMessagingTemplate.send(sqsQueueName, MessageBuilder.withPayload(message).build());
    }

    public void convertAndSend(UserPreferences userPreferences) {

        this.queueMessagingTemplate.convertAndSend(sqsQueueName, userPreferences);
    }

}
