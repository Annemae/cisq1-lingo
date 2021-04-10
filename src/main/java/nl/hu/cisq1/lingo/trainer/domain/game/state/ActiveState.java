package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;

public class ActiveState implements State {

    public ActiveState() { }

    @Override
    public Round createNewRound(Word wordToGuess, Game game) {
        if (game.getGameStatus() == WAITING_FOR_ROUND) {
            throw new InvalidGameStateException("Can't create a new round, because a round is already in progress.");
        }

        Round round = new Round(wordToGuess);

        game.addRound(round);
        game.setGameStatus(WAITING_FOR_ROUND);

        return round;
    }

    @Override
    public void takeGuess(String attempt, Game game) {
        Round currentRound = game.getCurrentRound();

        currentRound.takeGuess(attempt);

        Feedback lastFeedback = currentRound.getLastFeedback();
        lastFeedback.isGuessValid();

        if (currentRound.isOver() && lastFeedback.isWordGuessed()) {
            game.setGameStatus(PLAYING);

            int attempts = game.getCurrentRound().amountOfGuesses();
            int newScore = 5 * (5 - attempts) + 5;
            game.setScore(game.getScore() + newScore);

        } else if (currentRound.isOver() && !lastFeedback.isWordGuessed()) {
            game.changeState(new InactiveState());
            game.setGameStatus(ELIMINATED);
        }
    }
}
