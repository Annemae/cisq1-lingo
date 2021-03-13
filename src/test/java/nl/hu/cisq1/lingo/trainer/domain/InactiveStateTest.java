package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.game.state.InactiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.InvalidGameStateException;
import nl.hu.cisq1.lingo.trainer.domain.game.state.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InactiveStateTest {

    @Test
    @DisplayName("create new round works")
    void createNewRoundGivesError() {
        State inactiveState = new InactiveState();

        assertThrows(InvalidGameStateException.class, () ->
            inactiveState.createNewRound(null, null)
        );
    }

    @Test
    @DisplayName("take guess works")
    void takeGuessGivesError() {
        State inactiveState = new InactiveState();

        assertThrows(InvalidGameStateException.class, () ->
                inactiveState.takeGuess(null, null)
        );
    }
}