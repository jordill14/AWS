package org.paradise.microservice.userpreference.service.rest;


import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * HttpRequestInterceptor for populating additional information to the request as required. Populates proxy settings if
 * present.
 *
 * Created by Joel Wan on 14/11/2016.
 */
@Component
@Qualifier("httpRequestInterceptor")
public class HttpRequestInterceptor extends HttpComponentsClientHttpRequestFactory {

    private static final String PROXY_HOST_PROPERTY = "http.proxyHost";
    private static final String PROXY_PORT_PROPERTY = "http.proxyPort";
    private static final String PROXY_USER_PROPERTY = "http.proxyUser";
    private static final String PROXY_PASSWORD_PROPERTY = "http.proxyPassword";
    
    public HttpRequestInterceptor() {
        super();
    }
    
    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        Properties systemProperties = System.getProperties();
        String proxyHost = systemProperties.getProperty(PROXY_HOST_PROPERTY);
        Integer proxyPort = NumberUtils.toInt(systemProperties.getProperty(PROXY_PORT_PROPERTY), -1);
        String proxyUser = systemProperties.getProperty(PROXY_USER_PROPERTY);
        String proxyPassword = systemProperties.getProperty(PROXY_PASSWORD_PROPERTY);
        
        if (proxyHost != null && proxyPort != -1) {
            httpClientBuilder.setProxy(new HttpHost(proxyHost, proxyPort));

            if (proxyUser != null && proxyPassword != null) {
                UsernamePasswordCredentials passwordCredentials = new UsernamePasswordCredentials(proxyUser, proxyPassword);

                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), passwordCredentials);

                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                httpClientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
            }
        }

        this.setHttpClient(httpClientBuilder.build());

        return super.createRequest(uri, httpMethod);
    }

}
