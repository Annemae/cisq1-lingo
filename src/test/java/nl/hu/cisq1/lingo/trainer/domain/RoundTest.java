package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    //make guess
    //make wrong guess
    //word is an actual word
    //word guess is right length
    //

    @Test
    @DisplayName("gives back hint with first letter after starting new round")
    void roundIsMadeAndGivesFirstLetter() {
        Round round = new Round(Word.of("GITAAR"));

        assertEquals(List.of('G', '.', '.', '.', '.', '.'), round.getFirstHint().getHint()); //todo or aparte method met firstHint?
    }

    @Test
    @DisplayName("take a guess and get right feedback back")
    void takeValidGuess() {
        Round round = new Round(Word.of("GITAAR"));

        round.takeGuess("KOEKEN");

        Feedback feedback = round.getLastTurn().getFeedback();
        Hint hint = round.getLastTurn().getHint();

        assertEquals(List.of(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT), feedback.getMarks());
        assertEquals(List.of('G', '.', '.', '.', '.', '.'), hint.getHint());
    }
}