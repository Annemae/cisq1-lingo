package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest { //todo aparte testen?

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BROOD"));

        assertTrue(feedback.isWordGuessed());
        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BRAAD"));

        assertFalse(feedback.isWordGuessed());
        assertEquals(List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        Feedback feedback = Feedback.of("BROOD", Word.of("PAARD"));

        assertTrue(feedback.isGuessValid());
        assertEquals(List.of(ABSENT, PRESENT, ABSENT, ABSENT, CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("guess is invalid if all letters are invalid")
    void guessIsInvalid() {
        Feedback feedback = Feedback.of("KORT", Word.of("LANGER"));

        assertThrows(InvalidFeedbackException.class,
                feedback::isGuessValid);
        assertEquals(List.of(INVALID, INVALID, INVALID, INVALID), feedback.getMarks());
    }

    @Test
    @DisplayName("static constructor gives the same object back as new")
    void staticConstructorWorks() {
        Feedback expected = new Feedback("WORD", Word.of("WORD"));
        Feedback actual = Feedback.of("WORD", Word.of("WORD"));

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}