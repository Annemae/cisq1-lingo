package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.game.state.exception.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActiveStateTest {
    private static final Word BREAD = Word.of("BREAD");

    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(List.of("BEARS", "BEARS", "BEARS", "BEARS", "BEARS"),
                        0, true, ELIMINATED),
                Arguments.of(List.of("BEARS", "BEARS", "BEARS", "BEARS", "BREAD"),
                        5, true, PLAYING),
                Arguments.of(List.of("BEARS", "BEARS", "BEARS", "BREAD"),
                        10, true, PLAYING),
                Arguments.of(List.of("BEARS", "BEARS", "BREAD"),
                        15, true, PLAYING),
                Arguments.of(List.of("BEARS", "BREAD"),
                        20, true, PLAYING),
                Arguments.of(List.of("BREAD"),
                        25, true, PLAYING),
                Arguments.of(List.of("BEARS", "BEARS", "BEARS"),
                        0, false, WAITING_FOR_ROUND)
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("take guess gives back correct data")
    void takeGuessGivesCorrectData(List<String> attempts, int expectedScore, boolean expectedRoundIsOver, GameStatus status) {
        Game game = new Game(new DefaultLengthStrategy());
        State activeState = new ActiveState();
        activeState.createNewRound(BREAD, game);

        for(String attempt : attempts) {
            activeState.takeGuess(attempt, game);
        }

        assertEquals(expectedScore, game.getScore());
        assertEquals(expectedRoundIsOver, game.getCurrentRound().isOver());
        assertEquals(status, game.getGameStatus());
    }

    @Test
    @DisplayName("game state turns inactive after too many tries")
    void checkIfGameStateTurnsInactiveWhenEliminated() {
        Game gameSpy = spy(Game.class);
        State activeState = new ActiveState();

        Round round = activeState.createNewRound(Word.of("ATTEMPD"), gameSpy);
        activeState.takeGuess("ATTEMPT", gameSpy);
        activeState.takeGuess("ATTEMPT", gameSpy);
        activeState.takeGuess("ATTEMPT", gameSpy);
        activeState.takeGuess("ATTEMPT", gameSpy);
        activeState.takeGuess("ATTEMPT", gameSpy);

        assertEquals(ELIMINATED, gameSpy.getGameStatus());
        assertEquals(InactiveState.class, gameSpy.getState().getClass());
        assertTrue(round.isOver());

        verify(gameSpy).changeState(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("create round throws exception when a round is already ongoing")
    void createRoundThrowsInvalidGameStateException() {
        Game game = new Game(new DefaultLengthStrategy());
        State activeState = new ActiveState();

        activeState.createNewRound(BREAD, game);

        assertThrows(InvalidGameStateException.class, () ->
                activeState.createNewRound(BREAD, game)
        );
    }

    @Test
    @DisplayName("create a new round")
    void createRound() {
        Game game = new Game(new DefaultLengthStrategy());
        State activeState = new ActiveState();

        activeState.createNewRound(BREAD, game);

        assertEquals(1, game.getRounds().size());
        assertEquals(WAITING_FOR_ROUND, game.getGameStatus());
    }
}