package io.rasha.movie.info.config;

import io.rasha.movie.info.base.BaseMysqlContainerConfig;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("localtest")
@TestPropertySource(locations = "/application-localtest.yml")
public class ApiKeyAuthenticationFilterTest  extends BaseMysqlContainerConfig {

  @Autowired
  private MockMvc mockMvc;

  @Value("${api-key}")
  private String movie_insert_APIKey;

  @Test
  public void testValidApiKey() throws Exception {
    String validApiKey = movie_insert_APIKey;

    mockMvc.perform(get("/users")
            .header("Movie-Edit-API-KEY", validApiKey))
        .andExpect(status().isOk());
  }

  @Test
  public void testInvalidApiKey() throws Exception {
    String invalidApiKey = "invalid-api-key";

    mockMvc.perform(get("/users")
            .header("Movie-Edit-API-KEY", invalidApiKey))
        .andExpect(status().isUnauthorized());
  }
}
