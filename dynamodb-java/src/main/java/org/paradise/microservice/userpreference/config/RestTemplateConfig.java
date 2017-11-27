package org.paradise.microservice.userpreference.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.paradise.microservice.userpreference.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        httpComponentsClientHttpRequestFactory.setConnectTimeout(Constants.CONNECT_TIMEOUT);
        httpComponentsClientHttpRequestFactory.setReadTimeout(Constants.CONNECT_TIMEOUT);

        // Disable the default HttpClient CookieSpec selected so saved, unspecified, domain matched cookies would be
        // sent to RESTful services
        HttpClient httpClient = HttpClientBuilder.create()
                .disableCookieManagement()
                .useSystemProperties()
                .build();

        httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);

        return httpComponentsClientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {

        return new RestTemplate(clientHttpRequestFactory);
    }

}
