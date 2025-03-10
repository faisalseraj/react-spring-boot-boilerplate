package com.kitchensaver.backend.Config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kitchensaver.backend.Service.UserService;
import com.kitchensaver.backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final UserService userService;
//     private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//     public JwtAuthenticationFilter(UserService userService) {
//         this.userService = userService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain) throws ServletException, IOException {
//         final String authHeader = request.getHeader("Authorization");
        
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         try {
//             final String jwt = authHeader.substring(7);
//             final DecodedJWT decodedJWT = JwtUtil.verifyToken(jwt);
//             final String userEmail = decodedJWT.getSubject();
            
//             if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                 UserDetails userDetails = userService.loadUserByUsername(userEmail);
                
//                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                     userDetails,
//                     null,
//                     userDetails.getAuthorities()
//                 );
//                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authToken);
//             }
//         } catch (Exception e) {
//             logger.info("Cannot set user authentication: {}", e.getMessage());
//         }
        
//         filterChain.doFilter(request, response);
//     }
// }

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final DecodedJWT decodedJWT = jwtUtil.verifyToken(jwt);
            final String userEmail = decodedJWT.getSubject();
            
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmail);
                
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}