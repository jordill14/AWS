package org.paradise.microservice.userpreference;

import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
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

        JmsTemplate jmsTemplate = configurableApplicationContext.getBean(JmsTemplate.class);

        // Send a message with a POJO - the template reuse the message converter
        LOG.info("Sending an email message.");

        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setApbcn("This is Apbcn");
        userPreferences.setcNumber("This is cNumber");

        jmsTemplate.convertAndSend(Constants.JMS_TOPIC, userPreferences);
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
    public JmsListenerContainerFactory<?> mailboxJmsListenerFactory(
            ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer defaultJmsListenerContainerFactoryConfigurer) {

        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();

        // This provides all Spring Boot's default to this factory, including the message converter
        defaultJmsListenerContainerFactoryConfigurer.configure(defaultJmsListenerContainerFactory, connectionFactory);

        // You could still override some of Spring Boot's default if necessary.
        return defaultJmsListenerContainerFactory;
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
