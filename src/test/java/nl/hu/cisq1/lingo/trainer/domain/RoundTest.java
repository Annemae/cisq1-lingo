package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static final Word BREAD = Word.of("BREAD");

    @Test
    @DisplayName("round is over")
    void roundIsOver() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BREAD"); //WHEN

        assertTrue(round.isOver()); //THEN
    }

    @Test
    @DisplayName("round is not over")
    void roundIsNotOver() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BINGO"); //WHEN

        assertFalse(round.isOver()); //THEN
    }

    @Test
    @DisplayName("round is not over when it has just started")
    void roundIsNotOverWhenRoundJustStarted() {
        Round round = new Round(BREAD); //WHEN

        assertFalse(round.isOver()); //THEN
    }
}