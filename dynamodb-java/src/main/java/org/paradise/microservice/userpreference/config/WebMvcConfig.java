package org.paradise.microservice.userpreference.config;

import org.paradise.microservice.userpreference.feature.FeatureRepository;
import org.paradise.microservice.userpreference.feature.FeatureToggle;
import org.paradise.microservice.userpreference.interceptor.FeatureInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@EnableRedisHttpSession
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;


    @Bean
    @FeatureToggle(feature = "feature.redis.session.store", expectedToBeOn = false)
    public SessionRepository mapSessionRepository() {
        return new MapSessionRepository();
    }

//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }

    @Bean
    @FeatureToggle(feature = "feature.redis.session.store")
    public SessionRepository redisSessionRepository(RedisConnectionFactory connectionFactory) {
        return new RedisOperationsSessionRepository(connectionFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

        interceptorRegistry.addInterceptor(new FeatureInterceptor(new FeatureRepository(environment)));

        super.addInterceptors(interceptorRegistry);
    }

}
