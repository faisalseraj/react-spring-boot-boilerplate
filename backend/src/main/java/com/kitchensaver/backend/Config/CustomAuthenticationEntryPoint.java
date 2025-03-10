package com.kitchensaver.backend.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.kitchensaver.backend.Service.UserService;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        logger.info("authException :::" + authException.toString());
        response.setContentType("application/json");

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("""
                    {
                        "status": 401,
                        "message": "Access Denied: Token not available"
                    }
                    """);
        } else if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("""
                    {
                        "status": 403,
                        "message": "Access Denied: Full authentication is required to access this resource"
                    }
                    """);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("""
                    {
                        "status": 401,
                        "message": "Access Denied: Authentication required"
                    }
                    """);
        }
    }
}
