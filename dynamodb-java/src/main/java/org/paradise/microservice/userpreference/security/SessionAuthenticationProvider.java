package org.paradise.microservice.userpreference.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by terrence on 1/6/17.
 */
public class SessionAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(SessionAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        LOG.debug("Authenticate username / password: [{} / ********]", username);

        // validate making REST call
        boolean success = true;

        // likely your REST call will return the roles for the user
        String[] roles = new String[] {"ROLE_USER"};

        if (!success) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.createAuthorityList(roles));
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}