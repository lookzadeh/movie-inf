package io.rasha.movie.info.service;

import feign.FeignException;
import io.rasha.movie.info.dto.MovieDto;
import io.rasha.movie.info.mapper.MovieMapper;
import io.rasha.movie.info.model.Movie;
import io.rasha.movie.info.model.MovieResponse;
import io.rasha.movie.info.remote.client.OMDBApiClient;
import io.rasha.movie.info.repository.MovieRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MovieService {

  private final MovieRepository movieRepository;
  private final OMDBApiClient omdbApiClient;
  private final AwardService awardService;
  private final MovieMapper movieMapper;

  @Value("${omdb-api-key}")
  private String omdbAPIKey;

  public MovieService(MovieRepository movieRepository, OMDBApiClient omdbApiClient,
      AwardService awardService, MovieMapper movieMapper) {
    this.movieRepository = movieRepository;
    this.omdbApiClient = omdbApiClient;
    this.awardService = awardService;
    this.movieMapper = movieMapper;
  }

  public MovieResponse getMovieInfo(String name) {

    // TODO: we should manage multiple response from DB or API
    List<Movie> existingMovies = movieRepository.findByTitle(name);

    if (existingMovies.isEmpty()) {
      MovieDto movieDto = getMovieIngoFromOMDB(name);
      if (isValidData(movieDto)) {
        Movie movie = movieMapper.DtoToEntity(movieDto);

        boolean hasBestPicture = awardService.findAwardByNominee(movieDto.getTitle())
            .map(m -> m.getCategory().strip().equalsIgnoreCase("Best Picture")).orElse(false);
        movie.setHasBestPicture(hasBestPicture);

        saveMovieInfo(movie);
      } else {
        log.info("Data title or OMDBId is Null or empty!");
        return null;
      }
      return movieMapper.DtoToModel(movieDto);
    } else {
      return movieMapper.entityToModel(existingMovies.get(0));
    }
  }
  private void saveMovieInfo(Movie movie) {
    try {
      movieRepository.save(movie);
    } catch (DataIntegrityViolationException e) {
      log.info("Data is exists in DB" + e.getMessage());
    } catch (Exception e) {
      log.error("Error in saving in DB" + e.getMessage());
    }
  }

  private MovieDto getMovieIngoFromOMDB(String name) {
    try {
      return omdbApiClient.getMovieInfoByTitle(omdbAPIKey, name);
    } catch (FeignException e) {
      log.error("Error in getting movie info from OMDB" + e.getMessage());
    } catch (Exception e) {
      log.error("Error in getting movie info from OMDB" + e.getMessage());
    }
    return new MovieDto(); // Return a default instance or null
  }

  private static boolean isValidData(MovieDto movieDto) {
    return movieDto.getTitle() != null && !movieDto.getTitle().isEmpty() &&
        movieDto.getImdbId() != null && !movieDto.getImdbId().isEmpty();
  }
}
