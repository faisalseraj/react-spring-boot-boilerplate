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

    // @Override
    // public void commence(HttpServletRequest request,
    // HttpServletResponse response,
    // AuthenticationException authException) throws IOException {
    // response.setContentType("application/json");
    // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // response.getWriter().write("""
    // {
    // "status": 401,
    // "message": "Access Denied: Authentication required"
    // }
    // """);
    // }

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        logger.info("authException :::" + authException.toString());
        if (authException.getCause() instanceof AccessDeniedException) {
            // Token is present but with a different role
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set status to 403 Forbidden
            response.getWriter().write("""
                    {
                        "status": 403,
                        "message": "Access Denied: Insufficient privileges for this operation"
                    }
                    """);
        } else if (authException instanceof InsufficientAuthenticationException) {
            // No token or invalid token
            response.getWriter().write("""
                    {
                        "status": 401,
                        "message": "Access Denied: Invalid or missing authentication token"
                    }
                    """);
        } else {
            // Other types of authentication exceptions
            response.getWriter().write("""
                    {
                        "status": 401,
                        "message": "Access Denied: Authentication required"
                    }
                    """);
        }
    }
}