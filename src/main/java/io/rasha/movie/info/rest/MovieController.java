package io.rasha.movie.info.rest;

import io.rasha.movie.info.model.MovieResponse;
import io.rasha.movie.info.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, value = "/movie")
    public ResponseEntity<MovieResponse> getMovieInfo(@RequestParam String name) {
        log.info("getMovieInfo by: {}", name);

        MovieResponse movieInfo = movieService.getMovieInfo(name);
        return movieInfo != null ? ResponseEntity.ok(movieInfo) :
            ResponseEntity.notFound().build();
    }
}
