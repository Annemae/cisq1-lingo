package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import nl.hu.cisq1.lingo.trainer.domain.game.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.domain.game.state.State;
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
                        0, ELIMINATED),
                Arguments.of(List.of("BEARS", "BEARS", "BEARS", "BEARS", "BREAD"),
                        5, PLAYING),
                Arguments.of(List.of("BEARS", "BEARS", "BEARS", "BREAD"),
                        10, PLAYING),
                Arguments.of(List.of("BEARS", "BEARS", "BREAD"),
                        15, PLAYING),
                Arguments.of(List.of("BEARS", "BREAD"),
                        20, PLAYING),
                Arguments.of(List.of("BREAD"),
                        25, PLAYING),
                Arguments.of(List.of("BEARS", "BEARS", "BEARS"),
                        0, WAITING_FOR_ROUND)
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("take guess works correctly")
    void takeGuessWorks(List<String> attempts, int expectedScore, GameStatus status) {
        Game game = new Game(new DefaultLengthStrategy());
        State activeState = new ActiveState();

        activeState.createNewRound(BREAD, game);
        for(String attempt : attempts) {
            activeState.takeGuess(attempt, game);
        }

        assertEquals(expectedScore, game.getScore());
        assertEquals(status, game.getGameStatus());
    }

    @Test
    @DisplayName("gamestate turns inactive after too many tries")
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
        assertTrue(round.isOver());

        verify(gameSpy).changeState(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("throws exception when round is already ongoing")
    void createRoundGivesError() {
        Game game = new Game(new DefaultLengthStrategy());
        State activeState = new ActiveState();

        activeState.createNewRound(BREAD, game);

        assertThrows(InvalidGameStateException.class, () ->
                activeState.createNewRound(BREAD, game)
        );
    }

    @Test
    @DisplayName("create a new round")
    void createRoundWorks() {
        Game game = new Game(new DefaultLengthStrategy());
        State activeState = new ActiveState();

        activeState.createNewRound(BREAD, game);

        assertEquals(1, game.getRounds().size());
        assertEquals(WAITING_FOR_ROUND, game.getGameStatus());
    }
}