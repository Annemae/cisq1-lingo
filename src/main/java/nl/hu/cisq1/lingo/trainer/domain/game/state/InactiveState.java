package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;

public class InactiveState implements State {
    public InactiveState() {}

    @Override
    public Round createNewRound(Word wordToGuess) {
        throw new InvalidGameStateException("This game isn't active anymore, you can't create a new round.");
    }

    @Override
    public void takeGuess(String attempt) {
        throw new InvalidGameStateException("This game isn't active anymore, you can't take a guess.");
    }
}
