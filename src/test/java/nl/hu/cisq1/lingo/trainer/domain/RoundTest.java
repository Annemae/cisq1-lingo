package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static final Word BREAD = Word.of("BREAD");

    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(Collections.emptyList(),
                        0, List.of('B', '.', '.', '.', '.'),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of(List.of("BINGO"),
                        1, List.of('B', '.', '.', '.', '.'),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of(List.of("BEARS", "BREAK"),
                        2, List.of('B', 'R', 'E', 'A', '.'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),
                Arguments.of(List.of("BEARS", "BREAK", "BREAD"),
                        3, List.of('B', 'R', 'E', 'A', 'D'),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT))
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("taking a guess works correctly")
    void takeGuessWorks(List<String> guesses, int amountOfGuesses, List<Character> hintCharacters, List<Mark> marks) {
        Round round = new Round(BREAD);

        for(String guess : guesses) {
            round.takeGuess(guess);
        }

        assertEquals(amountOfGuesses, round.amountOfGuesses());
        assertEquals(hintCharacters, round.giveHint().getHintCharacters());
        assertEquals(marks, round.getRecentFeedback().getMarks());
    }

    @Test
    @DisplayName("round is over")
    void roundIsOver() {
        Round round = new Round(BREAD);

        round.takeGuess("BREAD");

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