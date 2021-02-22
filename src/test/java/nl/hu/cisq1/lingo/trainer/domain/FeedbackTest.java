package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    private static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(Word.of("BALEN"), "AVOND", List.of(PRESENT, ABSENT, ABSENT, PRESENT, ABSENT),
                        new Hint(List.of('.', '.', '.', '.', '.')), List.of('.', '.', '.', '.', '.')),
                Arguments.of(Word.of("SCHOEN"), "ACTIES", List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT, PRESENT),
                        new Hint(List.of('.', '.', '.', '.', '.', '.')), List.of('.', 'C', '.', '.', 'E', '.')),
                Arguments.of(Word.of("ALARM"), "ATOOM", List.of(CORRECT, ABSENT, ABSENT, ABSENT, CORRECT),
                        new Hint(List.of('A', '.', '.', '.', 'M')), List.of('A', '.', '.', '.', 'M')),
                Arguments.of(Word.of("BRAAD"), "BROOD", List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT),
                        new Hint(List.of('B', '.', '.', '.', 'D')), List.of('B', 'R', '.', '.', 'D')),
                Arguments.of(Word.of("BROOD"), "BROOD", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        new Hint(List.of('B', 'R', '.', '.', 'D')), List.of('B', 'R', 'O', 'O', 'D'))
        );
    }

    private static Stream<Arguments> provideWrongHintExamples() {
        return Stream.of(
                Arguments.of(Word.of("WATER"), "KORT", List.of(INVALID, INVALID, INVALID, INVALID),
                        new Hint(List.of('.', '.', '.', '.', '.'))),
                Arguments.of(Word.of("WATER"), "LANGER", List.of(INVALID, INVALID, INVALID, INVALID, INVALID, INVALID),
                        new Hint(List.of('.', '.', '.', '.', '.')))
        );
    }

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        Feedback feedback = Feedback.of("BROOD", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));

        assertTrue(feedback.isWordGuessed()); //todo klopt dit en andersom zetten?
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        Feedback feedback = Feedback.of("BROOD", List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT));

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        Feedback feedback = Feedback.of("BROOD", List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT));

        assertTrue(feedback.isGuessValid());
    }

    @Test
    @DisplayName("guess is invalid if all letters are invalid") //Omdat het altijd allemaal INVALID is en niet bijv. één INVALID.
    void guessIsInvalid() {
        Feedback feedback = Feedback.of("KORT", List.of(INVALID, INVALID, INVALID, INVALID));

        assertThrows(InvalidFeedbackException.class,
                feedback::isGuessValid);
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct")
    void hintIsCorrect(Word wordToGuess, String attempt, List<Mark> marks, Hint previousHint, List<Character> expectedHint) {
        Feedback feedback = Feedback.of(attempt, marks);

        List<Character> actualHint = feedback.giveHint(previousHint, wordToGuess).getHint();

        assertEquals(expectedHint, actualHint);
    }

    @ParameterizedTest
    @MethodSource("provideWrongHintExamples")
    @DisplayName("hint is incorrect when wordToGuess length or previousHint length differ from marks length")
    void hintLengthIsIncorrect(Word wordToGuess, String attempt, List<Mark> marks, Hint previousHint) { //Nu nog length maar kan later aangepast worden.
        Feedback feedback = Feedback.of(attempt, marks);

        assertThrows(InvalidHintException.class,
                () -> feedback.giveHint(previousHint, wordToGuess));
    }

//    @Test
//    @DisplayName("static constructor gives the same object back as new") //todo anders
//    void staticConstructorWorks() {
//        Feedback expected = new Feedback("BROOD", Word.of("BROOD"));
//        Feedback actual = Feedback.of("BROOD", Word.of("BROOD"));
//
//        assertEquals(expected.hashCode(), actual.hashCode());
//    }
}