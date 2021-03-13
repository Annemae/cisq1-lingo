package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;

import java.io.Serializable;

public class InactiveState implements State, Serializable {
    public InactiveState() {}

    @Override
    public Round createNewRound(Word wordToGuess, Game game) {
        throw new InvalidGameStateException("This game isn't active anymore, you can't create a new round.");
    }

    @Override
    public void takeGuess(String attempt, Game game) {
        throw new InvalidGameStateException("This game isn't active anymore, you can't take a guess.");
    }
}
