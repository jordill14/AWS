package org.paradise.microservice.userpreference.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@EnableRedisHttpSession
public class WebMvcConfig extends WebMvcConfigurerAdapter {

//    @Autowired
//    private Environment environment;


//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }

    @Bean
//    @FeatureToggle(feature = "feature.redis.session.store", expectedToBeOn = false)
    public SessionRepository mapSessionRepository() {
        return new MapSessionRepository();
    }

//    @Bean
//    @FeatureToggle(feature = "feature.redis.session.store")
//    public SessionRepository redisSessionRepository(RedisConnectionFactory connectionFactory) {
//        return new RedisOperationsSessionRepository(connectionFactory);
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
//
//        interceptorRegistry.addInterceptor(new FeatureInterceptor(new FeatureRepository(environment)));
//
//        super.addInterceptors(interceptorRegistry);
//    }

}
