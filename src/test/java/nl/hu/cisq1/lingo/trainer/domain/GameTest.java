package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private final Game game = new Game();

    @Test
    @DisplayName("creating a new round returns right feedback")
    void createRoundWorks() {
        Round round = game.createNewRound("BREAD");

        Hint hint = round.giveHint();

        assertEquals(List.of('B', '.', '.', '.', '.'), hint.getHintCharacters());
    }

    @Test
    @DisplayName("take guess returns right feedback")
    void takeGuessWorks() {
        game.createNewRound("WATER");
        game.takeGuess("WITCH");

        Round round = game.getCurrentRound();
        Feedback feedback = round.getLastFeedback();
        Hint hint = round.giveHint();

        assertEquals(List.of(CORRECT, ABSENT, CORRECT, ABSENT, ABSENT), feedback.getMarks());
        assertEquals(List.of('W', '.', 'T', '.', '.'), hint.getHintCharacters());
    }
}