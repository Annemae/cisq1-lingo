package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.game.Game;

import nl.hu.cisq1.lingo.trainer.domain.game.GameProgress;
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
    private static final Word BREAD = Word.of("BREAD");

    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(List.of("BEARS"),
                        0,
                        List.of(CORRECT, PRESENT, PRESENT, PRESENT, ABSENT),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of("BREAD"),
                        25,
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        List.of('B', 'R', 'E', 'A', 'D'))
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("give current progress works")
    void giveProgressWorks(List<String> attempts, int expectedScore, List<Mark> expectedMarks, List<Character> expectedHintCharacters) {
        Game game = new Game();
        game.createNewRound("BREAD");
        for(String attempt : attempts) {
            game.takeGuess(attempt);
        }

        GameProgress gameProgress = game.createGameResult();

        assertEquals(expectedScore, gameProgress.getScore());
        assertEquals(expectedMarks, gameProgress.getFeedback().getMarks());
        assertEquals(expectedHintCharacters, gameProgress.getHint().getHintCharacters());
    }


    @Test
    @DisplayName("create a round")
    void createRoundWorks() {
        Game game = new Game();

        game.createNewRound("WORTH");

        assertEquals(1, game.getRounds().size());
        assertEquals(WAITING_FOR_ROUND, game.getGameStatus());
    }

    @Test
    @DisplayName("current round is correct")
    void giveCorrectCurrentRound() {
        Game game = new Game();
        game.createNewRound("WORTH");
        game.takeGuess("WORTH");

        game.createNewRound("BREAD");

        assertEquals(BREAD, game.getCurrentRound().getWordToGuess());
    }
}