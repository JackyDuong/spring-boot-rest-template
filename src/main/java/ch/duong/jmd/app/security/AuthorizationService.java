package ch.duong.jmd.#APP_ABBREVIATION.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Administrator should have all accesses
 */
@Service
public class AuthorizationService {

    @Value("${admin.username}")
    private String adminUsername;

    @Autowired
    public AuthorizationService() {
    }
    public boolean isSuperAdmin(Authentication authentication) {
        return adminUsername.equals(authentication.getName());
    }

    public boolean isSuperAdmin(String username) {
        return adminUsername.equals(username);
    }

}
