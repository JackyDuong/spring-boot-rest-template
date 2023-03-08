package ch.duong.jmd.#APP_ABBREVIATION.utils;

import ch.duong.jmd.#APP_ABBREVIATION.security.JwtConfig;
import ch.duong.jmd.#APP_ABBREVIATION.security.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Date;

@SpringBootTest
class TokenProviderTest {

    @Test
    @Disabled
    void check_token() throws JsonProcessingException {
        final var secret = "SqGr/yBAIHd47K+iCn0mrCItIKzcXtz=ArTHX/1fT2it=";
        final var #APP_ABBREVIATIONSecret = "$5q$04$d/2ZtQ5ZGoqSCjDiCEuKKuHLPIkUJtjCsStFuL4Jg79z8qoz9.xUW";

        final var receivedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5MjEiLCJjcnNzLnNjb3BlcyI6eyJhdXRob3JpdGllcyI6W10sImFkbWluIjpmYWxzZX0sImlhdCI6MTYwMjA2MzIxMywiZXhwIjoxNjAyMDYzNTczfQ.-PDU_BvVq6JaXuRNMclTWXkkNKuAuufjibtThclBu8E";
        JwtConfig config = new JwtConfig();
        config.setSecret(#APP_ABBREVIATIONSecret);
        TokenProvider tp = new TokenProvider(config);
        final var claims = tp.getAllClaims(receivedToken, Keys.hmacShaKeyFor(#APP_ABBREVIATIONSecret.getBytes()));
        final var mapper = new ObjectMapper();
        System.out.println("claims : " + claims);
        System.out.println("claims : " + mapper.writeValueAsString(claims));


        final var authorities = "#APP_ABBREVIATION.scopes";

        final var token = Jwts.builder()
                .setSubject("921")
                .claim(authorities, null)
                .signWith(Keys.hmacShaKeyFor(#APP_ABBREVIATIONSecret.getBytes()), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 360 * 1000))
                .compact();

        System.out.println("secret : " + secret);
        System.out.println("token : " + token);

        final var generateSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String generateSecretEncode = Encoders.BASE64.encode(generateSecret.getEncoded());
        final var generateToken = Jwts.builder()
                .setSubject("921")
                .claim(authorities, null)
                .signWith(generateSecret)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 360 * 1000))
                .compact();

        System.out.println("generateSecretEncode : " + generateSecretEncode);
        System.out.println("generateSecret : " + generateSecret.getEncoded());
        System.out.println("generateToken : " + generateToken);


        SecretKey secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(secretkey.getEncoded());
        final var secretToken = Jwts.builder()
                .setSubject("921")
                .claim(authorities, null)
                .signWith(secretkey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 360 * 1000))
                .compact();

        System.out.println(secretkey.getEncoded());
        System.out.println("secretString : " + secretString);
        System.out.println("secretToken : " + secretToken);

        System.out.println("***************************************");

        final var decodeKey = Decoders.BASE64.decode(generateSecretEncode);
        final var body = Jwts.parserBuilder().setSigningKey(decodeKey).build().parseClaimsJws(generateToken).getBody();

        System.out.println(body);
    }
}
