package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameStateException;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.GameStatus.*;

public class ActiveState implements State {
    private final Game game;
    private final ScoreStrategy scoreStrategy;

    public ActiveState(Game game, ScoreStrategy scoreStrategy) {
        this.game = game;
        this.scoreStrategy = scoreStrategy;
    }

    @Override
    public Round createNewRound(Word wordToGuess) {
        if(game.getGameStatus() == WAITING_FOR_ROUND) {
            throw new InvalidGameStateException("Can't create a new round, because a round is already in progress.");
        }

        Round round = new Round(wordToGuess);

        game.addRound(round);
        game.setGameStatus(WAITING_FOR_ROUND);

        return round;
    }

    @Override
    public void takeGuess(String attempt) {
        Round currentRound = game.getCurrentRound();

        currentRound.takeGuess(attempt);

        List<Feedback> allFeedback = currentRound.getAllFeedback();

        if(currentRound.isOver() && allFeedback.size() <= 5) { //todo ??? inactivestate
            game.setGameStatus(PLAYING);

            int newScore = scoreStrategy.calculateScore(currentRound);
            game.setScore(game.getScore() + newScore);
        } else if (!currentRound.isOver() && allFeedback.size() >= 5) {
            game.changeState(new InactiveState(game));
            game.setGameStatus(ELIMINATED);
        }
    }
}
