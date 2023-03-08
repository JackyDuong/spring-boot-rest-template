package ch.duong.jmd.#APP_ABBREVIATION.service;

import ch.duong.jmd.#APP_ABBREVIATION.model.LoginCredential;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public interface AuthenticationService {

    /**
     * Authenticates the given loginCredential (username and password).
     * AuthenticationManager is configured in WebSecurityConfig.
     *
     * @param loginCredential The login and password pair to authenticate
     * @return An authentication interface if the user was successfully authenticated.
     * @throws AuthenticationException If the user could not be authenticated.
     */
    Authentication authenticate(LoginCredential loginCredential) throws AuthenticationException;

    /**
     * Builds a JWT token with the given username and roles.
     *
     * @return A string representation of the generated JWT token
     * @throws AuthenticationException If roles could not be transformed to json
     */
    String buildJwtToken(Authentication authentication) throws AuthenticationException;

    /**
     * Blacklist the token
     * @param authentication User authentication
     * @param token token to blacklist
     */
    void logout(Authentication authentication, String token);
}
