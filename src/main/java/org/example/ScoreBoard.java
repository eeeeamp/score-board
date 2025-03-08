package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ScoreBoard {

    private List<Game> registeredGames;
    private Set<Game> activeGames;

    public void startGame(Game game) {
        game.setHomeTeamScore(0);
        game.setAwayTeamScore(0);
    }
}
