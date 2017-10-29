package org.paradise.microservice.userpreference.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaslang.control.Try;
import org.paradise.microservice.userpreference.domain.AppConfiguration;
import org.paradise.microservice.userpreference.exception.UserPreferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class S3BucketService {

    private static final Logger LOG = LoggerFactory.getLogger(S3BucketService.class);

    private AppConfiguration appConfiguration;

    private LocalDateTime lastReadDateTime;

    @Value("${app.configuration.file}")
    private String appConfigurationFile;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;


    public AppConfiguration getAppConfiguration() {

        // Application Configuration is NULL or configuration has expired
        if (Objects.isNull(appConfiguration)
                || LocalDateTime.now().isAfter(lastReadDateTime.plusSeconds(appConfiguration.getExpiryInSeconds()))) {
            LOG.debug("Get application configuration file [{}] from S3 bucket", appConfigurationFile);

            appConfiguration = readAppConfigurationFromS3();
            lastReadDateTime = LocalDateTime.now();
        }

        return appConfiguration;
    }

    private AppConfiguration readAppConfigurationFromS3() {

        Resource resource = resourceLoader.getResource(appConfigurationFile);

        return Try.of(() -> objectMapper.readValue(resource.getInputStream(), AppConfiguration.class))
                .getOrElseThrow(UserPreferenceException::new);
    }

}
