package org.paradise.microservice.userpreference;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.paradise.microservice.userpreference.service.SqsQueueSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(App.class, args);

        // Send a message with a POJO via ActiveMQ - the template reuse the message converter
        JmsTemplate jmsTemplate = configurableApplicationContext.getBean(JmsTemplate.class);

        LOG.info("Sending an email message.");

        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setApbcn("This is Apbcn");
        userPreferences.setcNumber("This is cNumber");

        jmsTemplate.convertAndSend(Constants.JMS_TOPIC, userPreferences);

        // Send a message to AWS SQS
        SqsQueueSender sqsQueueSender = configurableApplicationContext.getBean(SqsQueueSender.class);

        sqsQueueSender.convertAndSend(userPreferences);
    }

    /**
     * Create default JMS listener container factory.
     *
     * @param connectionFactory connectionFactory
     * @param defaultJmsListenerContainerFactoryConfigurer defaultJmsListenerContainerFactoryConfigurer
     *
     * @return jmsListenerContainerFactory
     */
    @Bean
    public JmsListenerContainerFactory<?> mailboxJmsListenerFactory(ConnectionFactory connectionFactory,
                                                                    DefaultJmsListenerContainerFactoryConfigurer
                                                                            defaultJmsListenerContainerFactoryConfigurer) {

        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();

        // This provides all Spring Boot's default to this factory, including the message converter
        defaultJmsListenerContainerFactoryConfigurer.configure(defaultJmsListenerContainerFactory, connectionFactory);

        // You could still override some of Spring Boot's default if necessary.
        return defaultJmsListenerContainerFactory;
    }

    /**
     * Create AWS SQS / SNS message listener factory.
     *
     * @param amazonSQSAsync amazonSQSAsync
     *
     * @return simpleMessageListenerContainerFactory
     */
    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSAsync) {

        SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory = new SimpleMessageListenerContainerFactory();

        simpleMessageListenerContainerFactory.setAmazonSqs(amazonSQSAsync);
        simpleMessageListenerContainerFactory.setAutoStartup(Boolean.TRUE);
        simpleMessageListenerContainerFactory.setMaxNumberOfMessages(Constants.MAX_NUMBER_OF_MESSAGES);

        return simpleMessageListenerContainerFactory;
    }

    /**
     * Serialize message content to json using TextMessage.
     *
     * @return messageConverter
     */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {

        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();

        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("_type");

        return mappingJackson2MessageConverter;
    }

}
