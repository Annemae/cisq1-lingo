package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;

public interface State {
    Round createNewRound(Word wordToGuess, Game game);
    void takeGuess(String attempt, Game game);
}
