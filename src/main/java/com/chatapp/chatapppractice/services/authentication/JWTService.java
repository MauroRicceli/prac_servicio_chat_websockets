package com.chatapp.chatapppractice.services.authentication;

import com.chatapp.chatapppractice.models.constants.HeaderConstants;
import com.chatapp.chatapppractice.models.constants.TokenConstants;
import com.chatapp.chatapppractice.models.entities.TokenEntity;
import com.chatapp.chatapppractice.models.entities.UserEntity;
import com.chatapp.chatapppractice.models.enums.UserRole;
import com.chatapp.chatapppractice.repositories.TokenRepository;
import com.chatapp.chatapppractice.repositories.UserRepository;
import com.chatapp.chatapppractice.services.utils.UserVerificationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserVerificationService userVerificationService;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Returns a new access token for the user.
     * @param user Entity of the user.
     * @return generated access token.
     */
    public String generateAccessToken(final UserEntity user) {
        return generateToken(TokenConstants.TOKEN_TYPE_ACCESS_PREFIX, user, jwtExpiration);
    }

    /**
     * Returns a new refresh token for the user.
     * @param user Entity of the user.
     * @return generated refresh token.
     */
    public String generateRefreshToken(final UserEntity user) {
        return generateToken(TokenConstants.TOKEN_TYPE_REFRESH_PREFIX, user, jwtRefreshExpiration);
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
                .claims(Map.ofEntries(entry(TokenConstants.CLAIMS_USER_APP_USERNAME, user.getUsername()), entry(TokenConstants.CLAIMS_USER_ROLE, user.getUserRole()), entry(TokenConstants.CLAIMS_TOKEN_TYPE, tokentype)))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Verifies if the header is valid.
     * @param header with the prefix and the token.
     * @return true if its valid, declarative exception if it's not.
     */
    public boolean isHeaderValid(final String header) {

        if (header == null || !header.contains(HeaderConstants.BEARER_PREFIX)) {
            return false;
        }
        String token = header.substring(HeaderConstants.BEARER_LENGTH_HEADER);
        if (extractTokenType(token).equals(TokenConstants.TOKEN_TYPE_REFRESH_PREFIX)) {
            return isRefreshTokenValid(token);
        } else {
            if (extractTokenType(token).equals(TokenConstants.TOKEN_TYPE_ACCESS_PREFIX)) {
                return areTokenClaimsValid(token);
            }
        }
        return false;
    }

    /**
     * Verifies if the refresh token is valid.
     * @param token with info.
     * @return true if it's valid. Exception if not.
     */
    public boolean isRefreshTokenValid(final String token) {
        if (areTokenClaimsValid(token)) {
            final UserDetails usrDetails = userDetailsService.loadUserByUsername(extractTokenUsername(token));
            final UserEntity usr = userVerificationService.verifyUserExistenceFromEmailAndGetIt(usrDetails.getUsername());

            if (!usr.getUsername().equals(extractTokenAppUsername(token)) && !usr.getUserRole().toString().equals(extractTokenUserRole(token))) {
                return false;
            }

            Optional<TokenEntity> tkn = tokenRepository.findByToken(token);

            if (tkn.isEmpty()) {
                return false;
            }

            if (tkn.get().isRevoked() || tkn.get().isExpired()) {
               return false;
            }

            return true;

        }

        return false;


    }

    /**
     * Verifies if the claims of the token are valid.
     * @param token with the info.
     * @return True if is valid, exception if not.
     */
    public boolean areTokenClaimsValid(final String token) {
        if (isTokenExpired(token)) {
            return false;
        }
        if (extractTokenAppUsername(token) == null || extractTokenUserRole(token) == null) {
            return false;
        }

        UserRole.valueOf(extractTokenUserRole(token).toUpperCase()); //Si falla es por que el rol no existe en la aplicacion.

        if (!extractTokenUsername(token).contains("@")) {
            return false;
        }

        return true;
    }

    /**
     * Verify that the token has a valid encryption, then gets his username. <br>
     * It's the user's email in this application.
     * @param token with the username
     * @return the username as a String.
     */
    public String extractTokenUsername(final String token) {
        return Jwts.parser().verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Verify that the token has a valid encryption, then gets his token type.
     * @param token with a valid type. Refresh or access
     * @return the type of token as a String
     */
    private String extractTokenType(final String token) {
        Claims jwtToken = Jwts.parser().verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return jwtToken.get(TokenConstants.CLAIMS_TOKEN_TYPE, String.class);
    }

    /**
     * Verify that the token has a valid encryption, then gets the user role.
     * @param token with the info.
     * @return the role of the user as a String.
     */
    public String extractTokenUserRole(final String token) {
        Claims jwtToken = Jwts.parser().verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return jwtToken.get(TokenConstants.CLAIMS_USER_ROLE, String.class);
    }

    /**
     * Verify that the token has a valid encryption, then gets the token expiration date.
     * @param token with the info.
     * @return the expiration of the token as a Date.
     */
    private Date extractTokenExpiration(final String token) {
        return Jwts.parser().verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getExpiration();
    }

    /**
     * Verify if the token expiration it's earlier than now.
     * @param token with the info.
     * @return True/False if it's expired or not.
     */
    private boolean isTokenExpired(final String token) {
        return extractTokenExpiration(token).before(new Date());
    }

    /**
     * Verify that the token has a valid encryption, then gets the user username inside the app. <br>
     * <b>Not the token username!</b>.
     * @param token with the info.
     * @return the username of the user as a String.
     */
    private String extractTokenAppUsername(final String token) {
        Claims jwtToken = Jwts.parser().verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return jwtToken.get(TokenConstants.CLAIMS_USER_APP_USERNAME, String.class);
    }

    /**
     * Creates a secret key from the secret code.
     * @return the secret key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
