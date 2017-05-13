package org.paradise.microservice.userpreference.config;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.Properties;

/**
 * Created by terrence on 13/5/17.
 */
@Configuration
public class ProxyConfig {

    private static final String PROXY_HOST_PROPERTY = "http.proxyHost";
    private static final String PROXY_PORT_PROPERTY = "http.proxyPort";
    private static final String PROXY_USER_PROPERTY = "http.proxyUser";
    private static final String PROXY_PASSWORD_PROPERTY = "http.proxyPassword";

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory()  {

        /**
         * Consider using HttpClientBuilder.useSystemProperties() call in Apache HttpClient which use system properties
         * when creating and configuring default implementations, including http.proxyHost and http.proxyPort.
         *
         * In Apache HTTP Client 5, http.proxyUser and http.proxyPassword also defined in:
         *
         * @see org.apache.hc.client5.http.impl.auth.SystemDefaultCredentialsProvider
         */
        Properties systemProperties = System.getProperties();

        String proxyHost = systemProperties.getProperty(PROXY_HOST_PROPERTY);
        Integer proxyPort = NumberUtils.toInt(systemProperties.getProperty(PROXY_PORT_PROPERTY), -1);

        String proxyUser = systemProperties.getProperty(PROXY_USER_PROPERTY);
        String proxyPassword = systemProperties.getProperty(PROXY_PASSWORD_PROPERTY);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

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

        return new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
    }

}
