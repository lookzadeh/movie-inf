package io.rasha.movie.info.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwardDto {
  private Integer movieYear;
  private String nominee;
  private String category;
  private String additionalInfo;
  private Boolean isWon;
}
