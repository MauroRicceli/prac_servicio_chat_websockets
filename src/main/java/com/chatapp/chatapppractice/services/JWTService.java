package com.chatapp.chatapppractice.services;

import com.chatapp.chatapppractice.models.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.entry;

@Service
public class JWTService {
    /**
     * Our secret key used to make the encodes.
     */
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    /**
     * Our expiration for the access tokens.
     */
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    /**
     * Our expiration for refresh tokens.
     */
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long jwtRefreshExpiration;

    /**
     * Returns a new access token for the user.
     * @param user Entity of the user.
     * @return generated access token.
     */
    public String generateAccessToken(final UserEntity user) {
        return generateToken("access", user, jwtExpiration);
    }

    /**
     * Returns a new refresh token for the user.
     * @param user Entity of the user.
     * @return generated refresh token.
     */
    public String generateRefreshToken(final UserEntity user) {
        return generateToken("refresh", user, jwtRefreshExpiration);
    }

    /**
     * Generates a new token for the user
     * @param tokentype type of the token wanted to generate refresh/access
     * @param user Entity of the user
     * @param expiration Expiration wanted for the token
     * @return generated token
     */
    public String generateToken(final String tokentype, final UserEntity user, final long expiration) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claims(Map.ofEntries(entry("name", user.getUsername()), entry("role", user.getUserRole()), entry("type", tokentype)))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Creates a secret key from the secret code
     * @return the secret key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
