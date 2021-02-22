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

//    @Test
//    @DisplayName("gives back hint with first letter after starting new round")
//    void roundIsMadeAndGivesFirstLetter() {
//        Random random = Mockito.mock(Random.class); //todo uitzoeken hoe dit werkt.
//        Mockito.when(random.nextBoolean()).thenReturn(true);
//
//        Round round = new Round(Word.of("GITAAR"), random);
//        Feedback feedback = round.getFeedback().get(0);
//
//        assertEquals(List.of('G', '.', '.', '.', '.', '.'), round.getFeedback().get(0)); //todo or aparte method met firstHint?
//    }

//    @Test
//    @DisplayName("take a guess and get right feedback back")
//    void takeValidGuess() {
//        Random random = Mockito.mock(Random.class); //todo uitzoeken hoe dit werkt.
//        Mockito.when(random.nextBoolean()).thenReturn(true);
//
//        Round round = new Round(Word.of("GITAAR"), random);
//
//        round.takeGuess("KOEKEN");
//
//        assertEquals(List.of(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT), round.getLastFeedback().getMarks());
//        assertEquals(List.of('G', '.', '.', '.', '.', '.'), round.getLastHint());
//    }
}