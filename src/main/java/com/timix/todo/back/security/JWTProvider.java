package com.timix.todo.back.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(genValidSecretKey(secret).getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, UUID userId) {
        var claimsBuilder = Jwts.claims().subject(username);

        var claims = claimsBuilder.build();
        var now = new Date();
        var expiresIn = new Date(now.getTime() + expirationTime);

        return Jwts.builder().claims(claims)
                .issuedAt(now).expiration(expiresIn)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token);

        return true;
    }

    public Authentication getAuthentication(String token) {
        var claims = Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload();

        var authorities = Collections.<GrantedAuthority>emptyList();

        var principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private String genValidSecretKey(String secretKey) {
        var repeats = 1;

        while (secretKey.length() < 22) {
            secretKey = secretKey.repeat(repeats);
            repeats++;
        }

        return secretKey;
    }
}
