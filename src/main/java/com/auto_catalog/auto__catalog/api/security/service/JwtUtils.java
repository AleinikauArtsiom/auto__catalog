package com.auto_catalog.auto__catalog.api.security.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;
@Setter
@Getter
@Slf4j
@Component
@ConfigurationProperties(prefix = "spring.jwt.token")
public class JwtUtils {
    private String secret;
    private Long expirationTime;

    public String generateJwtToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.info("Jwt exception: " + e);
        }
        return false;
    }

    public Optional<String> getLoginFromToken(String token){
        try {
            return Optional.of(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        } catch (JwtException e){
            log.info("Jwt exception: " + e);
        }
        return Optional.empty();
    }

    public Optional<String> getTokenFromRequest(HttpServletRequest request){
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}