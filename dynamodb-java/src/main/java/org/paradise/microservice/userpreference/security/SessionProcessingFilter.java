package org.paradise.microservice.userpreference.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.paradise.microservice.userpreference.domain.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by terrence on 1/6/17.
 */
public class SessionProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(SessionProcessingFilter.class);

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SessionProcessingFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler authenticationSuccessHandler,
                                   AuthenticationFailureHandler authenticationFailureHandler) {

        super(defaultFilterProcessesUrl);

        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

        if (HttpMethod.POST.name().equals(request.getMethod())) {
            Login login = objectMapper.readValue(request.getReader(), Login.class);

            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        } else {
            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(StringUtils.EMPTY, StringUtils.EMPTY);
        }

        LOG.debug("Attempt to authenticate user [{}]", usernamePasswordAuthenticationToken.getName());

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        SecurityContextHolder.clearContext();

        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }

}
