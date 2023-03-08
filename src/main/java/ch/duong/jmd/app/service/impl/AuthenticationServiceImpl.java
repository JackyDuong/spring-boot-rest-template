package ch.duong.jmd.#APP_ABBREVIATION.service.impl;

import ch.duong.jmd.#APP_ABBREVIATION.model.LoginCredential;
import ch.duong.jmd.#APP_ABBREVIATION.security.AuthorizationService;
import ch.duong.jmd.#APP_ABBREVIATION.security.TokenProvider;
import ch.duong.jmd.#APP_ABBREVIATION.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

   private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

   @Value("${admin.username}")
   private String adminUsername;
   private final AuthenticationManager authenticationManager;
   private final AuthorizationService authorizationService;
   private final TokenProvider jwtTokenUtil;

   @Value("${recaptcha.validation.url}")
   private String wsUrl;
   @Value("${recaptcha.secret}")
   private String secret;

   @Override
   public Authentication authenticate(LoginCredential credential) throws AuthenticationException {
      try {
         return authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                     credential.getUsername(),
                     credential.getPassword()
               )
         );
      } catch (Exception e) {
         logger.error("Cannot log with nip {}", credential.getUsername());
         throw e;
      }
   }

   @Override
   public String buildJwtToken(Authentication authentication) throws AuthenticationException {
      return buildJwtToken(authentication.getName());
   }

   @Override
   public void logout(Authentication authentication, String token) {

   }

   private String buildJwtToken(String nip) throws AuthenticationException {
      return jwtTokenUtil.generate(nip);
   }
}
