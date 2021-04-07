package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InactiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(Collections.emptyList(),
                        0,
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT),
                        List.of('B', '.', '.', '.', '.'),
                        WAITING_FOR_ROUND),
                Arguments.of(List.of("BEARS"),
                        0,
                        List.of(CORRECT, PRESENT, PRESENT, PRESENT, ABSENT),
                        List.of('B', '.', '.', '.', '.'),
                        WAITING_FOR_ROUND),
                Arguments.of(List.of("BEARS", "BORED"),
                        0,
                        List.of(CORRECT, ABSENT, PRESENT, PRESENT, CORRECT),
                        List.of('B', '.', '.', '.', 'D'),
                        WAITING_FOR_ROUND),
                Arguments.of(List.of("BEARS", "BORED", "BREAD"),
                        15,
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        List.of('B', 'R', 'E', 'A', 'D'),
                        PLAYING),
                Arguments.of(List.of("BREAD"),
                        25,
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        List.of('B', 'R', 'E', 'A', 'D'),
                        PLAYING),
                Arguments.of(List.of("BEARS", "BORED", "BORED", "BORED", "BORED"),
                        0,
                        List.of(CORRECT, ABSENT, PRESENT, PRESENT, CORRECT),
                        List.of('B', '.', '.', '.', 'D'),
                        ELIMINATED)
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("take guess works correctly")
    void takeGuessWorks(List<String> attempts, int expectedScore, List<Mark> expectedMarks,
                           List<Character> expectedHintCharacters, GameStatus expectedGameStatus) {
        Game game = new Game(new DefaultLengthStrategy());
        game.createNewRound("BREAD");
        for(String attempt : attempts) {
            game.takeGuess(attempt);
        }

        GameProgress gameProgress = game.createGameProgress();

        assertEquals(expectedScore, gameProgress.getScore());
        assertEquals(expectedMarks, gameProgress.getFeedback().getMarks());
        assertEquals(expectedHintCharacters, gameProgress.getHint().getHintCharacters());
        assertEquals(expectedGameStatus, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back playing game status before starting round")
    void gameStatusPlaying() {
        Game game = new Game(new DefaultLengthStrategy());

        assertEquals(PLAYING, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back active state")
    void gameStateActiveState() {
        Game game = new Game(new DefaultLengthStrategy());

        assertEquals(ActiveState.class, game.getState().getClass());
    }

    @Test
    @DisplayName("gives back inactive state")
    void gameStateInactiveState() {
        Game game = new Game(new DefaultLengthStrategy());
        game.createNewRound("WORTH");

        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");
        game.takeGuess("WRONG");


        assertEquals(InactiveState.class, game.getState().getClass());
    }
}