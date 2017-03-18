package org.paradise.microservice.userpreference.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.paradise.microservice.userpreference.Constants;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by terrence on 11/3/17.
 */
@Component
public class JmsReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(JmsReceiver.class);

    @Autowired
    private ObjectMapper objectMapper;

    @JmsListener(destination = Constants.JMS_TOPIC, containerFactory = "mailboxJmsListenerFactory")
    public void receiveMessage(UserPreferences userPreferences) throws JsonProcessingException {

        LOG.info("Message received from ActiveMQ: [{}]", objectMapper.writeValueAsString(userPreferences));
    }

}
