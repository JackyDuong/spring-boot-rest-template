package ch.duong.jmd.#APP_ABBREVIATION.security;

import ch.duong.jmd.#APP_ABBREVIATION.exception.EmptyTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private TokenProvider jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException {

        final var header = req.getHeader(jwt.config.getHeader());

        try {
            if (header != null && header.startsWith(jwt.config.getPrefix())) {
                final var token = header.replace(jwt.config.getPrefix(), "").trim();
                if (token.isEmpty()) {
                    throw new EmptyTokenException("Empty token is passed to authenticator");
                }
                jwt.validate(token);

                final var username = jwt.getUsername(token);

                var authentication = new UsernamePasswordAuthenticationToken(username, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(req, res);
        } catch (AuthenticationException | ServletException | ExpiredJwtException | SignatureException e) {
            LOGGER.warn("Authentication exception", e);

            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (EmptyTokenException #APP_ABBREVIATIONException) {
            LOGGER.warn(#APP_ABBREVIATIONException.getMessage());
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
