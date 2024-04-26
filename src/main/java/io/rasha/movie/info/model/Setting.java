package io.rasha.movie.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;


@Data
@Entity
public class Setting extends BaseEntity {
  @Column(length = 100, nullable = false, unique = true)
  private String name;

  @Column(length = 1000)
  private String value;
}
