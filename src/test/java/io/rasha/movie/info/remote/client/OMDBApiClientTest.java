package io.rasha.movie.info.remote.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.rasha.movie.info.dto.MovieDto;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

public class OMDBApiClientTest {

  private WireMockServer wireMockServer;
  private OMDBApiClient omdbApiClient;

//  @BeforeEach
  public void setup() {
    wireMockServer = new WireMockServer();
    wireMockServer.start();
    WireMock.configureFor("localhost", wireMockServer.port());

    omdbApiClient = Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .contract(new SpringMvcContract())
        .target(OMDBApiClient.class, "http://localhost:" + wireMockServer.port());
  }

//  @AfterEach
  public void teardown() {
    wireMockServer.stop();
  }

  @Test
  public void getMovieInfoByTitleTest() {
    setup();
    stubFor(get(urlPathEqualTo("/"))
        .withQueryParam("apikey", equalTo("your_api_key"))
        .withQueryParam("t", equalTo("TheMatrix"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("{\"title\":\"The Matrix\",\"year\":\"1999\",\"imdbId\":\"tt0133093\"}")));

    MovieDto movie = omdbApiClient.getMovieInfoByTitle("your_api_key", "TheMatrix");
    assertEquals("TheMatrix", movie.getTitle());
    assertEquals("1999", movie.getYear());
    assertEquals("tt0133093", movie.getImdbId());
    teardown();
  }

// Similar test for getMovieInfoById
}