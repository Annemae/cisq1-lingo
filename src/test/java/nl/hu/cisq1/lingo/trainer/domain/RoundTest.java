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

    @MethodSource
    private static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of(Collections.emptyList(),
                        0, List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of("BINGO"),
                        1, List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of("BEARS", "BREAK"),
                        2, List.of('B', 'R', 'E', 'A', '.'))
        );
    }


    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("taking a guess works")
    void takeGuessWorks(List<String> guesses, int amountOfGuesses, List<Character> hintCharacters) {
        Round round = new Round(BREAD); //GIVEN

        for(String guess : guesses) {
            round.takeGuess(guess);
        }

        assertEquals(amountOfGuesses, round.amountOfGuesses()); //THEN
        assertEquals(hintCharacters, round.giveHint().getHintCharacters());
    }


    @Test
    @DisplayName("round gives back most recent feedback when asked")
    void getRecentFeedbackGivesFeedback() {
        Round round = new Round(BREAD); //GIVEN I MAKE A ROUND...
        round.takeGuess("BEARS"); //AND I TAKE TWO GUESSES
        round.takeGuess("BREAK");

        Feedback recentFeedback = round.getRecentFeedback().get(); //WHEN I ASK FOR FEEDBACK

        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT), recentFeedback.getMarks()); //THEN I SHOULD GET BACK THE MOST RECENT FEEDBACK
    }

    @Test
    @DisplayName("round gives empty optional when there is no feedback when asked")
    void getRecentFeedbackGivesEmptyOptional() {
        Round round = new Round(BREAD); //WHEN

        assertEquals(Optional.empty(), round.getRecentFeedback()); //THEN
    }

    @Test
    @DisplayName("round is over")
    void roundIsOver() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BREAD"); //WHEN

        assertTrue(round.isOver()); //THEN
    }

    @Test
    @DisplayName("round is over")
    void roundIsNotOver() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BINGO"); //WHEN

        assertFalse(round.isOver()); //THEN
    }

    @Test
    @DisplayName("round is not over when it just started")
    void roundIsNotOverWhenRoundJustStarted() {
        Round round = new Round(BREAD); //WHEN

        assertFalse(round.isOver()); //THEN
    }
}