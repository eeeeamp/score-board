package org.example;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Game {

  private Integer homeTeamScore;
  private Integer awayTeamScore;
  private String homeTeamName;
  private String awayTeamName;
}
