package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static final Word BREAD = Word.of("BREAD");
    private final Round round = new Round(BREAD);

    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of("BRAND",
                        List.of(CORRECT, CORRECT, PRESENT, ABSENT, CORRECT),
                        List.of('B', 'R', '.', '.', 'D')),
                Arguments.of("BATH",
                        List.of(INVALID, INVALID, INVALID, INVALID),
                        List.of('B', '.', '.', '.', '.'))
        );
    }

    @Test
    @DisplayName("gives back hint with first letter after starting new round")
    void roundIsMadeAndGivesFirstLetter() {
        Hint hint = round.giveHint(); //WHEN

        assertEquals(List.of('B', '.', '.', '.', '.'), hint.getHintCharacters()); //THEN
    }

    @Test
    @DisplayName("gives back all feedback")
    void givesBackAllFeedback() {
        round.takeGuess("BINGO"); //GIVEN
        round.takeGuess("BRAND");
        round.takeGuess("BREAD");

        List<Feedback> allFeedback = round.getAllFeedback(); //WHEN

        assertEquals(3, allFeedback.size()); //THEN
        assertEquals(3, round.amountAttemptsMade());
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("taking a guess works")
    void takingGuessWorks(String attempt, List<Mark> expectedMarks, List<Character> expectedHintCharacters) {
        round.takeGuess(attempt); //GIVEN

        Feedback feedback = round.getLastFeedback(); //WHEN
        Hint hint = round.giveHint();

        assertEquals(expectedMarks, feedback.getMarks()); //THEN
        assertEquals(expectedHintCharacters, hint.getHintCharacters());
    }

    @Test
    @DisplayName("round gives back latest feedback and hint")
    void givesBackLatestFeedbackAndHint() {
        round.takeGuess("BEARS"); //GIVEN
        round.takeGuess("BREAK");

        Feedback latestFeedback = round.getLastFeedback(); //WHEN
        Hint latestHint = round.giveHint();

        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT), latestFeedback.getMarks()); //THEN
        assertEquals(List.of('B', 'R', 'E', 'A', '.'), latestHint.getHintCharacters());
    }

    @Test
    @DisplayName("round is over")
    void roundIsOver() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BREAD"); //WHEN

        assertTrue(round.isOver()); //THEN
    }

    @Test
    @DisplayName("round is not over")
    void roundIsNotOver() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BINGO"); //WHEN

        assertFalse(round.isOver()); //THEN
    }
}