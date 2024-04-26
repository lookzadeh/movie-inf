package io.rasha.movie.info.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class MovieResponse {
  private String title;
  private String year;
  private String rated;
  private String released;
  private String runtime;
  private String genre;
  private String director;
  private String writer;
  private String actors;
  private String plot;
  private String language;
  private String country;
  private String awards;
  private String poster;
  private String ratings;
  private String metascore;
  @JsonProperty("imdb_rating")
  private String imdbRating;
  @JsonProperty("imdb_votes")
  private String imdbVotes;
  @JsonProperty("imdb_id")
  private String imdbID;
  private String type;
  private String dvd;
  @JsonProperty("box_office")
  private String boxOffice;
  private String production;
  private String website;

  @JsonProperty("is_best_picture")
  private boolean isBestPicture;
}