package org.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Game {

    private Integer homeTeamScore;
    private Integer awayTeamScore;
}
