package org.paradise.microservice.userpreference.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.control.Try;
import org.paradise.microservice.userpreference.domain.AppConfiguration;
import org.paradise.microservice.userpreference.exception.UserPreferenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class S3BucketService {

    private AppConfiguration appConfiguration;

    private LocalDateTime lastReadDateTime;

    @Value("${app.configuration.file}")
    private String sessionConfigurationFile;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;


    public AppConfiguration getAppConfiguration() {

        // Application Configuration is NULL or configuration has expired
        if (Objects.isNull(appConfiguration)
                || LocalDateTime.now().isAfter(lastReadDateTime.plusSeconds(appConfiguration.getExpiryInSeconds()))) {
            appConfiguration = readAppConfigurationFromS3();
            lastReadDateTime = LocalDateTime.now();
        }

        return appConfiguration;
    }

    private AppConfiguration readAppConfigurationFromS3() {

        Resource resource = resourceLoader.getResource(sessionConfigurationFile);

        return Try.of(() -> objectMapper.readValue(resource.getInputStream(), AppConfiguration.class))
                .getOrElseThrow(UserPreferenceException::new);
    }

}
