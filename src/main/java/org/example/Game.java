package org.example;

import lombok.*;

import java.time.LocalDateTime;

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
  private LocalDateTime registrationTime;
}
