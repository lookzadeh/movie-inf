package io.rasha.movie.info.remote.client;

import io.rasha.movie.info.dto.MovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OMDB-API-CLIENT", url = "${omdb-api-uri}"/*, configuration = FeignClientConfig.class*/)
public interface OMDBApiClient {

  @RequestMapping(method = RequestMethod.GET)
  MovieDto getMovieInfoByTitle(
      @RequestParam("apikey") String apikey,
      @RequestParam("t") String omdbTitle);

  @RequestMapping(method = RequestMethod.GET)
  MovieDto getMovieInfoById(
      @RequestParam("apikey") String apikey,
      @RequestParam("i") String omdbId);
}
