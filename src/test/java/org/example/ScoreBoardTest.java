package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> scoreBoard.startGame(game));
        assertEquals("Cannot start already active game", ex.getMessage());
    }

    @Test
    void startGame_shouldNotAllowInitializationOfAlreadyRegisteredGame() {
        // given
        Game game = new Game();
        registeredGames.add(game);

        // when & then
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> scoreBoard.startGame(game));
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
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> scoreBoard.finishGame(game));
        assertEquals("Cannot finish not active game", ex.getMessage());
    }
}
