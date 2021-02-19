package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    private final String attemptFiveLetters = "BROOD";

    @Test
    @DisplayName("word is guessed correctly")
    void wordIsGuessed() {
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attemptFiveLetters, marks);

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is guessed incorrectly")
    void wordIsNotGuessed() {
        List<Mark> marks = List.of(ABSENT, CORRECT, PRESENT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attemptFiveLetters, marks);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("guess is valid if no letters are invalid")
    void guessIsValid() {
        List<Mark> marks = List.of(ABSENT, CORRECT, PRESENT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attemptFiveLetters, marks);

        assertTrue(feedback.isGuessValid());
    }

    @Test
    @DisplayName("guess is invalid if all letters are invalid") //Omdat het altijd allemaal INVALID is en niet bijv. één INVALID.
    void guessIsInvalid() {
        List<Mark> marks = List.of(INVALID, INVALID, INVALID, INVALID, INVALID);
        Feedback feedback = new Feedback(attemptFiveLetters, marks);

        assertThrows(InvalidFeedbackException.class,
                feedback::isGuessValid);
    }


}