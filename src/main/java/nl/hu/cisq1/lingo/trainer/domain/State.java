package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public interface State {
    Feedback createNewRound(Word wordToGuess);
    Feedback takeGuess(String attempt);
}
