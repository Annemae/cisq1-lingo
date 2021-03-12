package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;

import java.util.List;

public interface State {
    Round createNewRound(Word wordToGuess);
    void takeGuess(String attempt);
}
