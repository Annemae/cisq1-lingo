package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public interface State {
    Round createNewRound(Word wordToGuess);
    void takeGuess(String attempt);
}
