package io.rasha.movie.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Award extends BaseEntity {

  @Column(name="movie_year", nullable = false)
  private Integer movieYear;
  // TODO: it can be convert to a enumeration in future
  @Column(length = 300, nullable = false)
  private String nominee;
  @Column(length = 300, nullable = false)
  private String category;
  @Column(name="additional_info", length = 3000)
  private String additionalInfo;
  @Column(name="is_won")
  private Boolean isWon;  //some values are not specified
}
