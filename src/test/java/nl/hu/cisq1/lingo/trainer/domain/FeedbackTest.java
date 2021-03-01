package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BROOD"), new Hint(List.of('B', '.', '.', '.', '.')));

        assertTrue(feedback.isWordGuessed());
        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        Feedback feedback = Feedback.of("BROOD", Word.of("BRAAD"), new Hint(List.of('B', '.', '.', '.', '.')));

        assertFalse(feedback.isWordGuessed());
        assertEquals(List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        Feedback feedback = Feedback.of("BROOD", Word.of("PAARD"), new Hint(List.of('P', '.', '.', '.', '.')));

        assertTrue(feedback.isGuessValid());
        assertEquals(List.of(CORRECT, PRESENT, ABSENT, ABSENT, CORRECT), feedback.getMarks());
    }

//    @Test
//    @DisplayName("guess is invalid if all letters are invalid")
//    void guessIsInvalid() {
//        Feedback feedback = Feedback.of("KORT", Word.of("LANGER"), new Hint(List.of('L', '.', '.', '.', '.', '.')));
//
//        assertFalse(feedback.isGuessValid());
//        assertEquals(List.of(INVALID, INVALID, INVALID, INVALID), feedback.getMarks());
//    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Feedback expected = new Feedback("WORD", Word.of("WORD"), new Hint(List.of('W', '.', '.', '.')));
        Feedback actual = Feedback.of("WORD", Word.of("WORD"), new Hint(List.of('W', '.', '.', '.')));

        assertEquals(expected.hashCode(), actual.hashCode());
        assertEquals(expected, actual);
    }
}