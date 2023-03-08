package ch.duong.jmd.#APP_ABBREVIATION.security;

import ch.duong.jmd.#APP_ABBREVIATION.model.LoginCredential;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static ch.duong.jmd.#APP_ABBREVIATION.Utils.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthApiTest {

    @Autowired
    private MockMvc mvc;

    private final String interventionPath = "/interventions";
    private final String username = "5439";
    private final String password = "118118";
    private final String authorizedSdis = "S-711";

    private static Map<String, String> tokenMaps = new HashMap<>();

    public static String getHeaderToken(MockMvc mvc, String username, String password) throws Exception {
        return "Bearer " + getToken(mvc, username, password);
    }

    private static String getToken(MockMvc mvc, String username, String password) throws Exception {

        if (tokenMaps.containsKey(username)) {
            return tokenMaps.get(username);
        }

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .content(asJsonString(new LoginCredential(username, password)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        final String token = JsonPath.parse(content).read("$.token");
        tokenMaps.put(username, token);
        return token;
    }

    @Test
    void whenGetInterventionsWithoutToken_thenUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(interventionPath)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenGetInterventionsWithEmptyToken_thenUnauthorized() throws Exception {
        final var header = "Bearer ";

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("sdis", authorizedSdis);

        expectUnauthorized(interventionPath, header, params);
    }

    @Test
    void whenGetInterventionsWithInvalidToken_thenUnauthorized() throws Exception {
        final var header = "Bearer eyJhbGciOiJIUzI1NiJ9.123456.789abcdefg";

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("sdis", authorizedSdis);

        expectUnauthorized(interventionPath, header, params);
    }

    @Test
    void whenGetInterventionsWithExpiredToken_thenUnauthorized() throws Exception {
        final var header = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MzUiLCJjcnNzLnNjb3BlcyI6Ilt7XCJyb2xlSWRcIjoyLFwic2Rpc0NvZGVcIjpcIlMtODg4XCIsXCJhZG1pblwiOmZhbHNlfSx7XCJyb2xlSWRcIjoyLFwic2Rpc0NvZGVcIjpcIlMtNzY1XCIsXCJhZG1pblwiOmZhbHNlfV0iLCJpYXQiOjE1ODQ2MjkxNjQsImV4cCI6MTU4NDYzMjc2NH0.ipRY8vyfBrapNmc3tUxS_qNpJ857ptZPC3HrZW2aEzQ";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("sdis", authorizedSdis);

        expectUnauthorized(interventionPath, header, params);
    }

    @Test
    void whenGetInterventionsWithValidTokenButUnauthorizedSdis_then_forbidden() throws Exception {

        final var header = getHeaderToken(mvc, "22", password);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("sdis", "S-690");

        mvc.perform(MockMvcRequestBuilders
                        .get(interventionPath)
                        .params(params)
                .header("Authorization", header)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private void expectUnauthorized(String path, String header, MultiValueMap<String, String> params) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(path)
                .params(params)
                .header("Authorization", header)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
