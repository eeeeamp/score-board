package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreBoardTest {

    @Test
    void startGame_shouldInitializeScore() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        Game game = new Game();

        // when
        scoreBoard.startGame(game);

        // then
        assertEquals(0, game.getHomeTeamScore());
        assertEquals(0, game.getAwayTeamScore());
    }
}
