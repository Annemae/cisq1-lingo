package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.game.state.exception.InvalidGameStateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InactiveStateTest {
    @Test
    @DisplayName("create new round throws exception")
    void createNewRoundThrowsInvalidGameStateException() {
        State inactiveState = new InactiveState();

        assertThrows(InvalidGameStateException.class, () ->
            inactiveState.createNewRound(null, null)
        );
    }

    @Test
    @DisplayName("take guess throws exception")
    void takeGuessThrowsInvalidGameStateException() {
        State inactiveState = new InactiveState();

        assertThrows(InvalidGameStateException.class, () ->
                inactiveState.takeGuess(null, null)
        );
    }
}