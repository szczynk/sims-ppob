package com.szczynk.simsppob.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration; // 12 * 60 * 60 * 1000 = 12 jam

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(key())
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(
                claims,
                userDetails.getUsername() // sebenarnya user email
        );
    }

    private JwtParser jwtParser() {
        return Jwts.parser().verifyWith(key()).build();
    }

    public String extractEmail(String token) {
        return jwtParser().parseSignedClaims(token).getPayload().getSubject();
    }

    private Date extractExpiration(String token) {
        return jwtParser().parseSignedClaims(token).getPayload().getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expire = extractExpiration(token);
        return expire.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);

        // userDetails.getUsername() sebenarnya user email
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
