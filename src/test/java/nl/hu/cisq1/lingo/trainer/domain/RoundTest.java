package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("gives back hint with first letter after starting new round")
    void roundIsMadeAndGivesFirstLetter() {
        Round round = new Round(Word.of("GITAAR"));
        Feedback feedback = round.getLastFeedback();
        Hint hint = feedback.getHint();

        assertEquals(List.of('G', '.', '.', '.', '.', '.'), hint.getHint());
    }

    @Test
    @DisplayName("take a valid guess and get right feedback back")
    void takeValidGuess() {
        Round round = new Round(Word.of("GITAAR"));

        Feedback feedback = round.takeGuess("GIETER");
        Hint hint = feedback.getHint();

        assertEquals(List.of(CORRECT, CORRECT, ABSENT, PRESENT, ABSENT, CORRECT), feedback.getMarks());
        assertEquals(List.of('G', 'I', '.', '.', '.', 'R'), hint.getHint());
    }

//    @Test
//    @DisplayName("take an invalid guess and get right feedback back")
//    void takeInvalidGuess() {
//        Round round = new Round(Word.of("GITAAR"));
//
//        assertThrows(InvalidGuessException.class,
//                () -> round.takeGuess("KOEK"));
//    }

    @Test
    @DisplayName("round is over")
    void roundIsOver() {
        Round round = new Round(Word.of("BANGER"));

        round.takeGuess("BANGER");

        assertTrue(round.isOver());
    }

    @Test
    @DisplayName("round is not over")
    void roundIsNotOver() {
        Round round = new Round(Word.of("BANGER"));

        round.takeGuess("KOEKEN");

        assertFalse(round.isOver());
    }

    @Test
    @DisplayName("round gives back most recent feedback")
    void givesBackMostRecentFeedback() {
        Round round = new Round(Word.of("BROOD"));
        round.takeGuess("BRAAD");

        assertEquals(List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT), round.getLastFeedback().getMarks());
    }
}