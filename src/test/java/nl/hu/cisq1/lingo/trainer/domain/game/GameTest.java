package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InactiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    @DisplayName("gives back correct gamestatus")
    void gameStatus() {
        Game game = new Game(new DefaultLengthStrategy());

        assertEquals(PLAYING, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back correct gamestatus")
    void gameStatus2() {
        Game game = new Game(new DefaultLengthStrategy());

        game.createNewRound("WORTH");

        assertEquals(WAITING_FOR_ROUND, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back correct gamestatus")
    void gameStatus3() {
        Game game = new Game(new DefaultLengthStrategy());
        game.createNewRound("WORTH");

        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");

        assertEquals(ELIMINATED, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back correct state")
    void gameState() {
        Game game = new Game(new DefaultLengthStrategy());

        assertEquals(ActiveState.class, game.getState().getClass());
    }

    @Test
    @DisplayName("gives back correct state")
    void gameState2() {
        Game game = new Game(new DefaultLengthStrategy());
        game.createNewRound("WORTH");

        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");


        assertEquals(InactiveState.class, game.getState().getClass());
    }


    @Test
    @DisplayName("gives back correct state")
    void gameState223() {
        Game game = new Game(new DefaultLengthStrategy());

        game.createNewRound("WORTH");

        assertEquals(1, game.getRounds().size());
    }
}