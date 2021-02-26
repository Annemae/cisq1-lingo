package nl.hu.cisq1.lingo.trainer.domain;

public interface State {
    Feedback createNewRound(Word wordToGuess);
}
