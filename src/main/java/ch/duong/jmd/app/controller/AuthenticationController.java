package ch.duong.jmd.#APP_ABBREVIATION.controller;

import ch.duong.jmd.#APP_ABBREVIATION.model.AuthToken;
import ch.duong.jmd.#APP_ABBREVIATION.model.LoginCredential;
import ch.duong.jmd.#APP_ABBREVIATION.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

   private final AuthenticationService authenticationService;

   @PostMapping(value = "/login")
   public ResponseEntity<AuthToken> login(@RequestBody LoginCredential loginCredential) throws AuthenticationException {
      var authentication = authenticationService.authenticate(loginCredential);
      final var token = authenticationService.buildJwtToken(authentication);

      return ResponseEntity.ok(new AuthToken(token));
   }


   @PostMapping(value = "/logout")
   @PreAuthorize("@authorizationService.exists(authentication)")
   public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token,
                                      Authentication authentication) throws AuthenticationException {
      authenticationService.logout(authentication, token);
      return ResponseEntity.ok().build();
   }

   @GetMapping(value = "/refresh")
   @PreAuthorize("@authorizationService.exists(authentication)")
   public ResponseEntity<AuthToken> refresh(@RequestHeader("Authorization") String authToken,
                                            Authentication authentication) {
      authenticationService.logout(authentication, authToken);
      final var token = authenticationService.buildJwtToken(authentication);

      return ResponseEntity.ok(new AuthToken(token));
   }
}
