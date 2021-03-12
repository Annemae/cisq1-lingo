package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static Game game;

    @Test
    @DisplayName("creating a new round returns right feedback")
    void createRoundWorks() {
        Round round = game.createNewRound("WATER");

        Hint hint = round.giveHint();

        assertEquals(List.of('W', '.', '.', '.', '.'), hint.getHintCharacters());
    }
}