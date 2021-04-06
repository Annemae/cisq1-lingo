package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InactiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.DefaultLengthStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(List.of("BEARS"),
                        0,
                        List.of(CORRECT, PRESENT, PRESENT, PRESENT, ABSENT),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of("BEARS", "BORED"),
                        0,
                        List.of(CORRECT, ABSENT, PRESENT, PRESENT, CORRECT),
                        List.of('B', '.', '.', '.', 'D')),
                Arguments.of(List.of("BREAD"),
                        25,
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        List.of('B', 'R', 'E', 'A', 'D')),
                Arguments.of(List.of("BEARS", "BORED", "BREAD"),
                        15,
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        List.of('B', 'R', 'E', 'A', 'D'))
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("give current progress works")
    void giveProgressWorks(List<String> attempts, int expectedScore, List<Mark> expectedMarks, List<Character> expectedHintCharacters) {
        Game game = new Game(new DefaultLengthStrategy());
        game.createNewRound("BREAD");
        for(String attempt : attempts) {
            game.takeGuess(attempt);
        }

        GameProgress gameProgress = game.createGameProgress();

        assertEquals(expectedScore, gameProgress.getScore());
        assertEquals(expectedMarks, gameProgress.getFeedback().getMarks());
        assertEquals(expectedHintCharacters, gameProgress.getHint().getHintCharacters());
    }

    @Test
    @DisplayName("create a round works")
    void createRoundWorks() {
        Game game = new Game(new DefaultLengthStrategy());

        game.createNewRound("WORTH");

        assertEquals(1, game.getRounds().size());
    }

    @Test
    @DisplayName("gives back playing game status")
    void gameStatusPlaying() {
        Game game = new Game(new DefaultLengthStrategy());

        assertEquals(PLAYING, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back waiting for round game status")
    void gameStatusWaitingForRound() {
        Game game = new Game(new DefaultLengthStrategy());

        game.createNewRound("WORTH");

        assertEquals(WAITING_FOR_ROUND, game.getGameStatus());
    }

    @Test
    @DisplayName("gives back eliminated game status")
    void gameStatusEliminated() {
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