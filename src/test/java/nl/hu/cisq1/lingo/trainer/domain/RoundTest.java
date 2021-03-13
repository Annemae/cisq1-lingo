package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static final Word BREAD = Word.of("BREAD");

//    @MethodSource
//    private static Stream<Arguments> provideGuessExamples() {
//        return Stream.of(
//        );
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("provideGuessExamples")
//    @DisplayName("taking a guess works")
//    void takeGuessWorks(List<Feedback> feedbackList, List<Character> expected) {
//        Round round = new Round(BREAD); //GIVEN
//
//        round.takeGuess("BINGO"); //WHEN
//
//        assertEquals(1, round.amountOfGuesses()); //THEN
//    }

    @Test
    @DisplayName("taking a guess works")
    void takeGuessWorks() {
        Round round = new Round(BREAD); //GIVEN

        round.takeGuess("BINGO"); //WHEN

        assertEquals(1, round.amountOfGuesses()); //THEN
    }



    @Test
    @DisplayName("gives back hint with first letter after starting new round and not taking guess yet")
    void getFirstHintWhenRoundIsMade() {
        Round round = new Round(BREAD); //GIVEN

        Hint hint = round.giveHint(); //WHEN

        assertEquals(List.of('B', '.', '.', '.', '.'), hint.getHintCharacters()); //THEN
    }

    @Test
    @DisplayName("round gives back most recent hint when asked") //TODO in before?
    void getRecentHint() {
        Round round = new Round(BREAD); //GIVEN I MAKE A ROUND...
        round.takeGuess("BEARS"); //AND I TAKE TWO GUESSES
        round.takeGuess("BREAK");

        Hint recentHint = round.giveHint(); //WHEN I ASK FOR HINT

        assertEquals(List.of('B', 'R', 'E', 'A', '.'), recentHint.getHintCharacters()); //THEN I SHOULD GET BACK THE MOST RECENT FEEDBACK
    }

    @Test
    @DisplayName("round gives back most recent feedback when asked")
    void getRecentFeedbackGivesFeedback() {
        Round round = new Round(BREAD); //GIVEN I MAKE A ROUND...
        round.takeGuess("BEARS"); //AND I TAKE TWO GUESSES
        round.takeGuess("BREAK");

        Feedback recentFeedback = round.getRecentFeedback(); //WHEN I ASK FOR FEEDBACK

        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT), recentFeedback.getMarks()); //THEN I SHOULD GET BACK THE MOST RECENT FEEDBACK
    }

    @Test
    @DisplayName("round gives error when there is no feedback when asked")
    void getRecentFeedbackGivesError() {
        Round round = new Round(BREAD); //GIVEN

        assertThrows(IllegalStateException.class, //WHEN AND THEN
                round::getRecentFeedback);
    }

    @Test
    @DisplayName("round gives back all feedback")
    void getAllFeedback() { //TODO ask...
        Round round = new Round(BREAD); //GIVEN I MAKE A NEW ROUND...
        round.takeGuess("BINGO"); //AND I TAKE TWO GUESSES
        round.takeGuess("BRAND");

        List<Feedback> allFeedback = round.getAllFeedback(); //WHEN I ASK FOR ALL THE FEEDBACK

        assertEquals(2, allFeedback.size()); //THEN I SHOULD GET TWO
        assertEquals(2, round.amountOfGuesses());
    }

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
}