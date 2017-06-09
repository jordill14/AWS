package org.paradise.microservice.userpreference.config;

import org.paradise.microservice.userpreference.security.SessionAuthenticationProvider;
import org.paradise.microservice.userpreference.security.SessionProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Created by terrence on 1/6/17.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String DEFAULT_FILTER_PROCESSES_URL = "/login";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {

        return new HeaderHttpSessionStrategy();
    }

    @Bean
    public SessionAuthenticationProvider sessionAuthenticationProvider() {

        return new SessionAuthenticationProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SessionProcessingFilter sessionProcessingFilter =
                new SessionProcessingFilter(DEFAULT_FILTER_PROCESSES_URL, authenticationSuccessHandler, authenticationFailureHandler);

        sessionProcessingFilter.setAuthenticationManager(this.authenticationManager);

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                    .authorizeRequests()
                        .antMatchers("/").permitAll()

                .and()
                    .addFilterBefore(sessionProcessingFilter, UsernamePasswordAuthenticationFilter.class);

//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .requestCache()
//                .requestCache(new NullRequestCache())
//                .and()
//                .httpBasic();

//        http.anonymous();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder, AuthenticationProvider authenticationProvider)
            throws Exception {

        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

}