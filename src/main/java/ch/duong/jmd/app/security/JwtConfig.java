package ch.duong.jmd.#APP_ABBREVIATION.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtConfig {
    @Value("${security.jwt.header}")
    private String header;
    @Value("${security.jwt.prefix:Bearer}")
    private String prefix;
    @Value("${security.jwt.expiration:1*60*60}")
    private int expiration;
    @Value("${security.jwt.authorities}")
    private String authorities;
    @Value("${security.jwt.secret}")
    private String secret;
}
