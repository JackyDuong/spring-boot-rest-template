package ch.duong.jmd.#APP_ABBREVIATION.controller;

import ch.duong.jmd.#APP_ABBREVIATION.model.LoginCredential;
import ch.duong.jmd.#APP_ABBREVIATION.security.TokenProvider;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static ch.duong.jmd.#APP_ABBREVIATION.Utils.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginApiTest {

   @Autowired
   private MockMvc mvc;

   @Autowired
   private TokenProvider tokenProvider;

   private final String apiPath = "/auth/login";
   private final String ldapUsername = "535";
   private final String ldapPassword = "118118";

   @Test
   void whenLoginWithoutCredentials_thenBadRequestAndTokenDoesNotExist() throws Exception {
      mvc.perform(MockMvcRequestBuilders
                  .post(apiPath)
                  .content("")
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").doesNotExist());
   }

   @Test
   void whenLoginRequestWithInvalidCredentials_thenUnauthorizedAndTokenDoesNotExist() throws Exception {
      expectUnauthorizedWithoutToken(new LoginCredential("1234", "1234"));
   }

   @Test
   void whenLoginRequestWithEmptyCredentials_thenUnauthorizedAndTokenDoesNotExist() throws Exception {
      expectUnauthorizedWithoutToken(new LoginCredential("", ""));
   }

   @Test
   void whenLoginRequestWithInvalidLdapCredentials_thenUnauthorizedAndTokenDoesNotExist() throws Exception {
      expectUnauthorizedWithoutToken(new LoginCredential(ldapUsername, "123"));
   }

   @Test
   void loginRequest_with_invalid_Ldap_credentials_then_unauthorized() throws Exception {
      expectUnauthorizedWithTokenNoCaptcha(new LoginCredential(ldapUsername, "123"));
   }

   @Test
   void whenLoginRequestWithAdminCredentials_thenOkAndTokenExists() throws Exception {
      expectOkWithToken(new LoginCredential("administrator", "administrator"));
   }


   @Test
   void whenLoginRequestWithValidLdapCredentials_thenOkAndTokenExists() throws Exception {
      expectOkWithToken(new LoginCredential(ldapUsername, ldapPassword));
   }

   @Test
   void whenLoginRequestWithValidCredentials_thenTokenIsValid() throws Exception {

      MvcResult result = mvc.perform(MockMvcRequestBuilders
                  .post(apiPath)
                  .content(asJsonString(new LoginCredential(ldapUsername, ldapPassword)))
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
            .andReturn();

      final var content = result.getResponse().getContentAsString();
      final String token = JsonPath.parse(content).read("$.token");
      assert (tokenProvider.validate(token));
   }

   private void expectUnauthorizedWithoutToken(LoginCredential loginCredential) throws Exception {
      mvc.perform(MockMvcRequestBuilders
                  .post(apiPath)
                  .content(asJsonString(loginCredential))
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").doesNotExist());
   }

   private void expectOkWithToken(LoginCredential loginCredential) throws Exception {
      mvc.perform(MockMvcRequestBuilders
                  .post(apiPath)
                  .content(asJsonString(loginCredential))
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
   }

   private void expectUnauthorizedWithTokenNoCaptcha(LoginCredential loginCredential) throws Exception {
      mvc.perform(MockMvcRequestBuilders
                  .post(apiPath)
                  .content(asJsonString(loginCredential))
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
   }


}
