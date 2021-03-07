package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameStateException;

public class InactiveState implements State {
    private final Game game;

    public InactiveState(Game game) {
        this.game = game;
    }

    @Override
    public Round createNewRound(Word wordToGuess) {
        throw new InvalidGameStateException("This game isn't active anymore, you can't create a new round.");
    }

    @Override
    public void takeGuess(String attempt) {
        throw new InvalidGameStateException("This game isn't active anymore, you can't take a guess.");
    }
}
