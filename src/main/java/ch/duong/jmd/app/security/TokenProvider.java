package ch.duong.jmd.#APP_ABBREVIATION.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class TokenProvider implements Serializable {

    public JwtConfig config;
    private final Key #UPPER_CASE_APP_ABBREVIATION_TOKEN_SECRET_KEY;

    public TokenProvider(JwtConfig config) {
        this.config = config;
        this.#UPPER_CASE_APP_ABBREVIATION_TOKEN_SECRET_KEY = Keys.hmacShaKeyFor(config.getSecret().getBytes());
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getIssueAt(String token, String secret) {
        return getIssueAt(token, Keys.hmacShaKeyFor(secret.getBytes()));
    }

    public Date getIssueAt(String token, Key secret) {
        return getClaim(token, Claims::getIssuedAt, secret);
    }

    public Date getExpirationDate(String token, String secret) {
        return getExpirationDate(token, Keys.hmacShaKeyFor(secret.getBytes()));
    }

    public Date getExpirationDate(String token, Key secret) {
        return getClaim(token, Claims::getExpiration, secret);
    }

    public Date getExpirationDate(String token) {
        return getExpirationDate(token, #UPPER_CASE_APP_ABBREVIATION_TOKEN_SECRET_KEY);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        return getClaim(token, claimsResolver, #UPPER_CASE_APP_ABBREVIATION_TOKEN_SECRET_KEY);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        return getClaim(token, claimsResolver, Keys.hmacShaKeyFor(secret.getBytes()));
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver, Key secret) {
        final Claims claims = getAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    public String getClaim(String token, String claimName) {
        return getClaim(token, claimName, #UPPER_CASE_APP_ABBREVIATION_TOKEN_SECRET_KEY);
    }

    public String getClaim(String token, String claimName, String secret) {
        return getClaim(token,claimName, Keys.hmacShaKeyFor(secret.getBytes()));
    }

    public String getClaim(String token, String claimName, Key secret) {
        try {
            return new ObjectMapper().writeValueAsString(getAllClaims(token, secret).get(claimName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getAllClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token.replace("\"", ""))
                .getBody();
    }

    private Boolean isExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim(config.getAuthorities(), "")
                .signWith(#UPPER_CASE_APP_ABBREVIATION_TOKEN_SECRET_KEY, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + config.getExpiration() * 1000))
                .compact();
    }

    public boolean validate(String token) {
        return !isExpired(token);
    }
}

