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
    // validations
    if (activeGames.contains(game)) {
      throw new IllegalStateException("Cannot start already active game");
    }
    if (registeredGames.contains(game)) {
      throw new IllegalStateException("Cannot start already finished game");
    }

    // initialize score
    game.setHomeTeamScore(0);
    game.setAwayTeamScore(0);

    // register game in tracking systems
    registeredGames.add(game);
    activeGames.add(game);
  }

  public void finishGame(Game game) {
    if (activeGames.contains(game)) {
      activeGames.remove(game);
    } else {
      throw new IllegalStateException("Cannot finish not active game");
    }
  }

  public void updateGameScore(Game game, int newHomeTeamScore, int newAwayTeamScore) {
    game.setHomeTeamScore(newHomeTeamScore);
    game.setAwayTeamScore(newAwayTeamScore);
  }
}
