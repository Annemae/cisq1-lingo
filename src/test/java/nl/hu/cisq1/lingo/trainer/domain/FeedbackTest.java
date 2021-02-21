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
                Arguments.of(Word.of("BALEN"), "AVOND", List.of('.', '.', '.', '.', '.'), List.of('.', '.', '.', '.', '.')),
                Arguments.of(Word.of("SCHOEN"), "ACTIES", List.of('.', '.', '.', '.', '.', '.'), List.of('.', 'C', '.', '.', 'E', '.')),
                Arguments.of(Word.of("ALARM"), "ATOOM", List.of('A', '.', '.', '.', 'M'), List.of('A', '.', '.', '.', 'M')),
                Arguments.of(Word.of("BRAAD"), "BROOD", List.of('B', '.', '.', '.', 'D'), List.of('B', 'R', '.', '.', 'D'))
        );
    }

//    private static Stream<Arguments> provideWrongHintExamples() {
//        return Stream.of(
//                Arguments.of(Word.of("WATER"), "BROOD", List.of('.', '.', '.', '.', '.')),
//                Arguments.of(Word.of("SCHOEN"), "ACTIES", List.of('.', '.', '.', '.', '.'))
//        );
//    }

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BROOD"));

        assertTrue(feedback.isWordGuessed()); //todo klopt dit?
        assertEquals(feedback.getMarks(), List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("AREND"));

        assertFalse(feedback.isWordGuessed());
        assertEquals(feedback.getMarks(), List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT));
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BRAAD"));

        assertTrue(feedback.isGuessValid());
        assertEquals(feedback.getMarks(), List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT));
    }

    @Test
    @DisplayName("guess is invalid if all letters are invalid") //Omdat het altijd allemaal INVALID is en niet bijv. één INVALID.
    void guessIsInvalid() {
        Feedback feedback = Feedback.of("KORT", Word.of("BROOD"));

        assertThrows(InvalidFeedbackException.class,
                feedback::isGuessValid);
        assertEquals(feedback.getMarks(), List.of(INVALID, INVALID, INVALID, INVALID));
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct")
    void hintIsCorrect(Word wordToGuess, String attempt, List<Character> previousHint, List<Character> expectedHint) {
        Feedback feedback = Feedback.of(attempt, wordToGuess);

        List<Character> actualHint = feedback.giveHint(previousHint, wordToGuess, feedback.getMarks());

        assertEquals(expectedHint, actualHint);
    }

//    @ParameterizedTest
//    @MethodSource("provideWrongHintExamples")
//    @DisplayName("hint is incorrect when wordToGuess length or previousHint length differ from marks length")
//    void hintLengthIsIncorrect(Word wordToGuess, String attempt, List<Character> previousHint) { //Nu nog length maar kan later aangepast worden.
//        Feedback feedback = Feedback.of(attempt, wordToGuess);
//
//        assertThrows(InvalidHintException.class,
//                () -> feedback.giveHint(previousHint, wordToGuess, feedback.getMarks()));
//    }
}