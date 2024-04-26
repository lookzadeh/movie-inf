package io.rasha.movie.info.mapper;

import io.rasha.movie.info.dto.RatingDto;
import io.rasha.movie.info.model.MovieResponse;
import io.rasha.movie.info.dto.MovieDto;
import io.rasha.movie.info.model.Movie;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MovieMapper {
  @Mapping(source = "ratings", target = "ratings", qualifiedByName = "ListToString")
  Movie DtoToEntity(MovieDto movieDto);
  @Mapping(source = "ratings", target = "ratings", qualifiedByName = "ListToString")
  MovieResponse DtoToModel(MovieDto movieDto);
  MovieResponse entityToModel(Movie movie);

  @Named("ListToString")
  default String ListToString(List<RatingDto> list) {
    return list.stream().map(Object::toString)
        .collect(java.util.stream.Collectors.joining(",", "", ""));
  }
}
