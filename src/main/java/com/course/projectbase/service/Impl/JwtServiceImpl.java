package com.course.projectbase.service.Impl;

import com.course.projectbase.common.TokenType;
import com.course.projectbase.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j(topic = "JWT-SERVICE")
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiryMinutes}")
    private long expMinutes;

    @Value("${jwt.expDay}")
    private long expDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Override
    public String generateAccessToken(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generate Access Token for user: {} with authorities: {}", userId, authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return genToken(claims, username);
    }

    @Override
    public String generateRefreshToken(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generate Refresh Token for user: {} with authorities: {}", userId, authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return genRefreshToken(claims, username);
    }

    @Override
    public String extractUsername(String token, TokenType type) {
        log.info("Extract username from token {} with type {}", token, type);

        return extractClaims(type, token, Claims::getSubject);
    }

    private <T> T extractClaims(TokenType type, String token, Function<Claims, T> claimsExtractor) {
        log.info("Extract claim from token {} with type {}", token, type);
        final Claims claims = extractAllClaim(token, type);
        return claimsExtractor.apply(claims);
    }

    private Claims extractAllClaim(String token, TokenType type) {
        try {
            return Jwts.parser().setSigningKey(accessKey).parseClaimsJws(token).getBody();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new AccessDeniedException("Access denied! error: " + e.getMessage());
        }
    }

    private String genToken(Map<String, Object> claims, String username) {
        log.info("Generate Access Token for user: {} with username: {}", username, claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expMinutes))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private String genRefreshToken(Map<String, Object> claims, String username) {
        log.info("Generate Access Token for user: {} with username: {}", username, claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expDay))
                .signWith(getKey(TokenType.ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
        switch (type) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            default -> throw new IllegalStateException("Invalid token type: " + type);
        }
    }
}
