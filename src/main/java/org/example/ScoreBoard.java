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
    validateGameIsNotActive(game);
    validateGameIsRegistered(game);

    initializeGame(game);

    registeredGames.add(game);
    activeGames.add(game);
  }

  public void finishGame(Game game) {
    validateGameCanBeFinished(game);
    activeGames.remove(game);
  }

  public void updateGameScore(Game game, int newHomeTeamScore, int newAwayTeamScore) {
    validateProvidedScoresAreCorrect(newHomeTeamScore, newAwayTeamScore);
    validateGameScoreCanBeUpdated(game);

    game.setHomeTeamScore(newHomeTeamScore);
    game.setAwayTeamScore(newAwayTeamScore);
  }

  public List<String> getGamesSummary() {
    return registeredGames.stream()
        .filter(ScoreBoard::isScoreInitialized)
        .sorted(Comparator.comparingInt(ScoreBoard::calculateTotalScore).reversed()
                .thenComparing(Game::getRegistrationTime, Comparator.reverseOrder()))
        .map(this::formatGameResult)
        .toList();
  }

  private void validateGameIsRegistered(Game game) {
    if (registeredGames.contains(game)) {
      throw new IllegalStateException("Cannot start already finished game");
    }
  }

  private void validateGameIsNotActive(Game game) {
    if (activeGames.contains(game)) {
      throw new IllegalStateException("Cannot start already active game");
    }
  }

  private void validateGameCanBeFinished(Game game) {
    validateGameIsNotActive(game, "Cannot finish not active game");
  }

  private void validateGameScoreCanBeUpdated(Game game) {
    validateGameIsNotActive(game, "Cannot update score of not active game");
  }

  private void validateGameIsNotActive(Game game, String errorMessage) {
    if (!activeGames.contains(game)) {
      throw new IllegalStateException(errorMessage);
    }
  }

  private static void validateProvidedScoresAreCorrect(int newHomeTeamScore, int newAwayTeamScore) {
    if (newHomeTeamScore < 0 || newAwayTeamScore < 0) {
      throw new IllegalArgumentException("At least one of the provided parameters is negative number. " +
              "Game will not be updated");
    }
  }

  private static void initializeGame(Game game) {
    game.setHomeTeamScore(0);
    game.setAwayTeamScore(0);
  }

  private static int calculateTotalScore(Game game) {
    return game.getHomeTeamScore() + game.getAwayTeamScore();
  }

  private static boolean isScoreInitialized(Game game) {
    return game.getHomeTeamScore() != null && game.getAwayTeamScore() != null;
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
