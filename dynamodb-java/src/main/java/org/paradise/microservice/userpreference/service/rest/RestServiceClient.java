package org.paradise.microservice.userpreference.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RESTful service client, based on Spring RestTemplate and Apache HTTP Client.
 */
@Component
public class RestServiceClient {

    private RestTemplate restTemplate;

    @Autowired
    public RestServiceClient(@Qualifier("httpRequestInterceptor") HttpRequestInterceptor httpRequestInterceptor) {

        restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(httpRequestInterceptor);
        restTemplate.setMessageConverters(createHttpMessageConverter());
        restTemplate.setErrorHandler(createResponseErrorHandler());
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method,
                                          HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables)
            throws RestClientException {

        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * Create default HTTP Request converter list.
     *
     * @return HttpMessageConverter created
     */
    private List<HttpMessageConverter<?>> createHttpMessageConverter() {

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        return messageConverters;
    }

    /**
     * By pass the default Exception Handler. When make RESTful call, disable any error handler when use this
     * restTemplate, and directly back error message to service(s) or controller(s).
     *
     * @return ResponseErrorHandler created
     */
    private ResponseErrorHandler createResponseErrorHandler() {

        return new ResponseErrorHandler() {

            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };
    }

}
