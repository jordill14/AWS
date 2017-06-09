package org.paradise.microservice.userpreference.security;

import com.google.common.base.Splitter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by terrence on 8/6/17.
 */
@Component
public class SessionAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String UNIFORM_RESOURCE_LOCATOR = "URL";
    
    /**
     * Authenticate login rquest and redirec to target URL, e.g., http://localhost:8080/login?URL=/health.
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String redirectUrl = Splitter.on("&").withKeyValueSeparator("=").split(request.getQueryString())
                .entrySet().stream()
                .filter(map -> UNIFORM_RESOURCE_LOCATOR.equals(map.getKey()))
                .findFirst()
                .get()
                .getValue();

        response.sendRedirect(redirectUrl);
    }

}
