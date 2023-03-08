package ch.duong.jmd.#APP_ABBREVIATION.controller;

import ch.duong.jmd.#APP_ABBREVIATION.security.AuthApiTest;
import ch.duong.jmd.#APP_ABBREVIATION.security.AuthorizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public abstract class #UPPER_CASE_APP_ABBREVIATIONControllerTest {

   final String adminUsername = "administrator";
   final String adminPassword = "administrator";

   protected boolean initialized = false;

   @Autowired
   protected MockMvc mvc;

   @MockBean
   protected AuthorizationService authorizationService;

   protected ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

   abstract String getBaseUrl();

   protected String getUrl() {
      return getUrl(null);
   }

   /**
    * Get the endpoint URL
    *
    * @param path Path to endpoint
    * @return endpoint URL
    */
   protected String getUrl(String path) {
      if (path == null || path.isEmpty()) {
         return getBaseUrl();
      }
      if (path.startsWith("?") || path.startsWith("/")) {
         return getBaseUrl() + path;
      }

      return getBaseUrl() + "/" + path;
   }

   protected String getUserTestAuthorizationHeader(String username, String password) throws Exception {

      return AuthApiTest.getHeaderToken(mvc, username, password);
   }
}
