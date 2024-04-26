package io.rasha.movie.info.repository;

import io.rasha.movie.info.model.Award;
import io.rasha.movie.info.model.Movie;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
  List<Movie> findByTitle(@Param("nominee") String nominee);
}
