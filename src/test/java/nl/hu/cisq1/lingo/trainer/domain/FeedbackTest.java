package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    private static final Word BREAD = Word.of("BREAD");

    private static Stream<Arguments> provideMarkExamples() {
        return Stream.of(
                Arguments.of(Feedback.of("", BREAD),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of(Feedback.of("BINGO", BREAD),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),

                Arguments.of(Feedback.of("BAARD", BREAD),
                        List.of(CORRECT, PRESENT, ABSENT, PRESENT, CORRECT)),
                Arguments.of(Feedback.of("BEARS", BREAD),
                        List.of(CORRECT, PRESENT, PRESENT, PRESENT, ABSENT)),
                Arguments.of(Feedback.of("BAAAN", BREAD),
                        List.of(CORRECT, ABSENT, ABSENT, CORRECT, ABSENT)),

                Arguments.of(Feedback.of("BREAK", BREAD),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),
                Arguments.of(Feedback.of("BREAD", BREAD),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),

                Arguments.of(Feedback.of("CACAO", BREAD),
                        List.of(INVALID, INVALID, INVALID, INVALID, INVALID)),
                Arguments.of(Feedback.of("BROTHER", BREAD),
                        List.of(INVALID, INVALID, INVALID, INVALID, INVALID, INVALID, INVALID)),
                Arguments.of(Feedback.of("BATH", BREAD),
                        List.of(INVALID, INVALID, INVALID, INVALID))
        );
    }

    private static Stream<Arguments> provideEqualsExamples() {
        Feedback feedback = new Feedback("BREAD", BREAD);
        return Stream.of(
                Arguments.of(feedback,
                        feedback,
                        true),
                Arguments.of(feedback,
                        null,
                        false),
                Arguments.of(feedback,
                        new Feedback("BREAD", Word.of("ACORN")),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideMarkExamples")
    @DisplayName("the correct marks are given back")
    void marksAreCorrect(Feedback feedback, List<Mark> expected) {
        List<Mark> actual = feedback.getMarks();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        Feedback feedback = Feedback.of("BREAD", BREAD);

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        Feedback feedback = Feedback.of("BRAND", BREAD);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        Feedback feedback = Feedback.of("BRAND", BREAD);

        assertTrue(feedback.isGuessValid());
    }

    @Test
    @DisplayName("guess is invalid if all letters are invalid")
    void guessIsInvalid() {
        Feedback feedback = Feedback.of("BROTHER", BREAD);

        assertThrows(InvalidGuessException.class,
                feedback::isGuessValid);
    }

    @ParameterizedTest
    @MethodSource("provideEqualsExamples")
    @DisplayName("equals works correctly")
    void equalsWorks(Feedback feedbackOne, Feedback feedbackTwo, boolean isEqual) {
        assertEquals(feedbackOne.equals(feedbackTwo), isEqual);
    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Feedback expected = new Feedback("BREAD", BREAD);
        Feedback actual = Feedback.of("BREAD", BREAD);

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}