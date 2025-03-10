package com.kitchensaver.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kitchensaver.backend.Service.UserService;

import jakarta.annotation.PostConstruct;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component

public class JwtUtil {
    private static String secret = "secret";
    private static int expirationTime = 86400000;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        logger.info("secret from env is heree:::" + secret);
        JwtUtil.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpirationTime(int expirationTime) {
        logger.info("expirationTime from env is heree:::" + expirationTime);
        JwtUtil.expirationTime = expirationTime;
    }

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") int expirationTime) {

        logger.info("secret and expirationTime from env is heree:::" + secret + ":::" + expirationTime);

        JwtUtil.secret = secret;
        JwtUtil.expirationTime = expirationTime;
    }

    // @PostConstruct

    public static String generateToken(String username, String role, Long id) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withClaim("id", id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);
    }

    public static String extractUsername(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public static String extractRole(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("role").asString();
    }

    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static Long getUserIdByDecodedToken(DecodedJWT decodedJWT) {
        return Long.parseLong(decodedJWT.getClaim("id").asString());
    }
}