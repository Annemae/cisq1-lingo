package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    private final String attemptFiveLetters = "BROOD";

    private static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("BALEN", "AVOND", List.of(PRESENT, ABSENT, ABSENT, ABSENT, ABSENT),
                        List.of('.', '.', '.', '.', '.'), List.of('.', '.', '.', '.', '.')),
                Arguments.of("SCHOEN", "ACTIES", List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT, PRESENT),
                        List.of('.', '.', '.', '.', '.', '.'), List.of('.', 'C', '.', '.', 'E', '.')),
                Arguments.of("ALARM", "ATOOM", List.of(CORRECT, ABSENT, ABSENT, ABSENT, CORRECT),
                        List.of('A', '.', '.', '.', 'M'), List.of('A', '.', '.', '.', 'M')),
                Arguments.of("BRAAD", "BROOD", List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT),
                        List.of('B', '.', '.', '.', 'D'), List.of('B', 'R', '.', '.', 'D'))
        );
    }

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = Feedback.of(attemptFiveLetters, marks);

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        List<Mark> marks = List.of(ABSENT, CORRECT, PRESENT, CORRECT, ABSENT);
        Feedback feedback = Feedback.of(attemptFiveLetters, marks);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        List<Mark> marks = List.of(ABSENT, CORRECT, PRESENT, CORRECT, ABSENT);
        Feedback feedback = Feedback.of(attemptFiveLetters, marks);

        assertTrue(feedback.isGuessValid());
    }

    @Test
    @DisplayName("guess is invalid if all letters are invalid") //Omdat het altijd allemaal INVALID is en niet bijv. één INVALID.
    void guessIsInvalid() {
        List<Mark> marks = List.of(INVALID, INVALID, INVALID, INVALID, INVALID);
        Feedback feedback = Feedback.of(attemptFiveLetters, marks);

        assertThrows(InvalidFeedbackException.class,
                feedback::isGuessValid);
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("feedback object gives back hint")
    void giveTheRightHintBack(String wordToGuess, String attempt, List<Mark> marks, List<Character> previousHint, List<Character> expectedHint) {
        Feedback feedback = Feedback.of(attempt, marks);

        List<Character> actualHint = feedback.giveHint(previousHint, wordToGuess, marks);

        assertEquals(expectedHint, actualHint);
    }
}