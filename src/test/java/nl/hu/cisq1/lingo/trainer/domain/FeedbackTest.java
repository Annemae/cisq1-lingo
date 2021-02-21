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

class FeedbackTest { //TODO TEST IK WEL GENOEG NU?
    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BROOD"));

        assertTrue(feedback.isWordGuessed()); //todo klopt dit en andersom zetten?
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

//    @Test
//    @DisplayName("static constructor gives the same object back as new") //todo anders
//    void staticConstructorWorks() {
//        Feedback expected = new Feedback("BROOD", Word.of("BROOD"));
//        Feedback actual = Feedback.of("BROOD", Word.of("BROOD"));
//
//        assertEquals(expected.hashCode(), actual.hashCode());
//    }
}