package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    @DisplayName("creating a new round returns right feedback")
    void createRoundWorks() {
        Feedback feedback = game.createNewRound("PAPIER");
        Hint hint = feedback.getHint();

        assertEquals(List.of('P', '.', '.', '.', '.', '.'), hint.getHint());
        assertEquals(List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT), feedback.getMarks());
    }

    @Test
    @DisplayName("can't create a round while another round is in progress")
    void createRoundDoesNotWork() {
        game.createNewRound("BAKKER");

        assertThrows(InvalidGameStateException.class,
                () -> game.createNewRound("WATER"));
    }

    @Test
    @DisplayName("round gives back most recent feedback")
    void givesBackCurrentRound() {
        game.createNewRound("WATER");
        Round round = game.getCurrentRound();
        Feedback feedback = round.getLastFeedback();
        Hint hint = feedback.getHint();

        assertEquals(List.of('W', '.', '.', '.', '.'), hint.getHint());
        assertEquals(List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT), feedback.getMarks());
    }

    @Test
    @DisplayName("take guess returns right feedback")
    void takeGuessWorks() {
        game.createNewRound("WATER");

        game.takeGuess("BETER");

        Round round = game.getCurrentRound();
        Feedback feedback = round.getLastFeedback();
        Hint hint = feedback.getHint();

        assertEquals(List.of('W', '.', 'T', 'E', 'R'), hint.getHint());
        assertEquals(List.of(CORRECT, PRESENT, CORRECT, CORRECT, CORRECT), feedback.getMarks());
    }
}