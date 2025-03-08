package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

  private List<Game> registeredGames;
  private Set<Game> activeGames;
  private ScoreBoard scoreBoard;

  @BeforeEach
  void setUp() {
    registeredGames = new ArrayList<>();
    activeGames = new HashSet<>();
    scoreBoard = new ScoreBoard(registeredGames, activeGames);
  }

  @Test
  void startGame_shouldInitializeScore_andAddGameToRegisteredAndAllGames() {
    // given
    scoreBoard = new ScoreBoard(registeredGames, activeGames);
    Game game = new Game();

    // when
    scoreBoard.startGame(game);

    // then
    assertEquals(0, game.getHomeTeamScore());
    assertEquals(0, game.getAwayTeamScore());
    assertTrue(registeredGames.contains(game));
    assertTrue(activeGames.contains(game));
  }

  @Test
  void startGame_shouldNotAllowInitializationOfActiveGame() {
    // given
    Game game = new Game();
    activeGames.add(game);

    // when & then
    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> scoreBoard.startGame(game));
    assertEquals("Cannot start already active game", ex.getMessage());
  }

  @Test
  void startGame_shouldNotAllowInitializationOfAlreadyRegisteredGame() {
    // given
    Game game = new Game();
    registeredGames.add(game);

    // when & then
    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> scoreBoard.startGame(game));
    assertEquals("Cannot start already finished game", ex.getMessage());
  }

  @Test
  void finishGame_shouldRemoveGameFromActiveGames_butNotFromRegisteredGames() {
    // given
    Game game = new Game();
    activeGames.add(game);
    registeredGames.add(game);

    // when
    scoreBoard.finishGame(game);

    // then
    assertFalse(activeGames.contains(game));
    assertTrue(registeredGames.contains(game));
  }

  @Test
  void finishGame_shouldNotAllowToRemoveNotActiveGame() {
    // given
    Game game = new Game();

    // when & then
    IllegalStateException ex =
        assertThrows(IllegalStateException.class, () -> scoreBoard.finishGame(game));
    assertEquals("Cannot finish not active game", ex.getMessage());
  }

  @Test
  void should_updateGameScore() {
    // given
    Game game = new Game();
    game.setHomeTeamScore(2);
    game.setAwayTeamScore(4);
    activeGames.add(game);

    int newHomeTeamScore = 3;
    int newAwayTeamScore = 4;

    // when
    scoreBoard.updateGameScore(game, newHomeTeamScore, newAwayTeamScore);

    // then
    assertEquals(newHomeTeamScore, game.getHomeTeamScore());
    assertEquals(newAwayTeamScore, game.getAwayTeamScore());
  }

  @ParameterizedTest(name = "Test for negative numbers validation with home score {0} and away score {1}")
  @CsvSource({"-1, 0", "0, -1", "-1, -1"})
  void updateGameScore_shouldNotAllowProvidingNegativeNumbers(int newHomeTeamScore, int newAwayTeamScore) {
    // given
    Game game = new Game();
    activeGames.add(game);

    // when & then
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> scoreBoard.updateGameScore(game, newHomeTeamScore, newAwayTeamScore));
    assertEquals("At least one of the provided parameters is negative number. Game will not be updated",
        ex.getMessage());
  }

  @Test
  void updateGameScore_shouldNotAllowToUpdateScoreOfNotActiveGame() {
    // given
    Game game = new Game();
    int newHomeTeamScore = 3;
    int newAwayTeamScore = 4;

    // when & then
    IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> scoreBoard.updateGameScore(game, newHomeTeamScore, newAwayTeamScore));
    assertEquals("Cannot update score of not active game", ex.getMessage());
  }

  @Test
  void gamesSummary_shouldReturnGamesResultsOrderedByTotalScore() {
    // given
    Game game1 = new Game(1, 0, "Poland", "Germany");
    Game game2 = new Game(3, 2, "France", "Italy");
    registeredGames.add(game1);
    registeredGames.add(game2);

    // when
    List<String> gamesSummary = scoreBoard.getGamesSummary();

    // then
    assertEquals(2, gamesSummary.size());
    assertEquals("France 3 - Italy 2", gamesSummary.getFirst());
    assertEquals("Poland 1 - Germany 0", gamesSummary.getLast());
  }
}
