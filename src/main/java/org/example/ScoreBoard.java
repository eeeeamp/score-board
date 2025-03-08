package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
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
    // validate numbers
    if (newHomeTeamScore < 0 || newAwayTeamScore < 0) {
      throw new IllegalArgumentException("At least one of the provided parameters is negative number. " +
              "Game will not be updated");
    }

    // validate game state
    if (activeGames.contains(game)) {
      game.setHomeTeamScore(newHomeTeamScore);
      game.setAwayTeamScore(newAwayTeamScore);
    } else {
      throw new IllegalStateException("Cannot update score of not active game");
    }
  }

  public List<String> getGamesSummary() {
    return registeredGames.stream()
        .filter(game -> game.getHomeTeamScore() != null && game.getAwayTeamScore() != null)
        .sorted(Comparator.comparingInt((Game game) -> game.getHomeTeamScore() + game.getAwayTeamScore())
                .reversed()
                .thenComparing(Game::getRegistrationTime, Comparator.reverseOrder()))
        .map(this::formatGameResult)
        .toList();
  }

  private String formatGameResult(Game game) {
    StringBuilder sb = new StringBuilder();
    sb.append(game.getHomeTeamName())
        .append(" ")
        .append(game.getHomeTeamScore())
        .append(" - ")
        .append(game.getAwayTeamName())
        .append(" ")
        .append(game.getAwayTeamScore());
    return sb.toString();
  }
}
