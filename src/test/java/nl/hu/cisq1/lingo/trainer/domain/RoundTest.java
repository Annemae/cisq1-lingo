package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static final Word BREAD = Word.of("BREAD");

    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(Collections.emptyList(),
                        0, false, List.of('B', '.', '.', '.', '.'),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),

                Arguments.of(List.of("BINGO"),
                        1, false, List.of('B', '.', '.', '.', '.'),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of(List.of("BEARS", "BREAK"),
                        2, false, List.of('B', 'R', 'E', 'A', '.'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),
                Arguments.of(List.of("BEARS", "BREAK", "BREAD"),
                        3, true, List.of('B', 'R', 'E', 'A', 'D'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),

                Arguments.of(List.of("BEARS", "BREAK", "BREAK", "BREAK", "BREAK", "BREAD"),
                        5, true, List.of('B', 'R', 'E', 'A', '.'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),
                Arguments.of(List.of("BEARS", "BREAK", "BREAK", "BREAK", "BREAK", "BREAK", "BREAK", "BREAK", "BREAD"),
                        5, true, List.of('B', 'R', 'E', 'A', '.'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),

                Arguments.of(List.of("INVALID"),
                        0, false, List.of('B', '.', '.', '.', '.'),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of(List.of("BREAK", "INVALID"),
                        1, false, List.of('B', 'R', 'E', 'A', '.'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT))
                );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("taking a guess works correctly")
    void takeGuessWorks(List<String> guesses, int expectedAmountOfGuesses, boolean expectedRoundIsOver,
                        List<Character> expectedHintCharacters, List<Mark> expectedMarks) {
        Round round = new Round(BREAD);

        for(String guess : guesses) { round.takeGuess(guess); }

        Feedback lastFeedback = round.getLastFeedback();
        assertEquals(expectedAmountOfGuesses, round.amountOfGuesses());
        assertEquals(expectedRoundIsOver, round.isOver());
        assertEquals(expectedHintCharacters, round.giveHint().getHintCharacters());
        assertEquals(expectedMarks, lastFeedback.getMarks());
    }

    @Test
    @DisplayName("round is over")
    void roundIsOver() {
        Round round = new Round(BREAD);

        round.takeGuess("BREAD");

        assertTrue(round.isOver());
    }

    @Test
    @DisplayName("round is over by too many attempts")
    void roundIsOverTooManyAttempts() {
        Round round = new Round(BREAD);

        round.takeGuess("BINGO");
        round.takeGuess("BINGO");
        round.takeGuess("BINGO");
        round.takeGuess("BINGO");
        round.takeGuess("BINGO");

        assertTrue(round.isOver());
    }

    @Test
    @DisplayName("round is not over")
    void roundIsNotOver() {
        Round round = new Round(BREAD);

        round.takeGuess("BINGO");

        assertFalse(round.isOver());
    }
}