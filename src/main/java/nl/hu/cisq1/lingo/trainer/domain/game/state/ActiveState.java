package nl.hu.cisq1.lingo.trainer.domain.game.state;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.domain.game.Game;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;

public class ActiveState implements State {
    private final Game game;

    public ActiveState(Game game) {
        this.game = game;
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

        if(currentRound.isOver() && allFeedback.size() <= 5) {
            game.setGameStatus(PLAYING);

            int attempts = game.getCurrentRound().amountOfGuesses();
            int newScore =  5 * (5 - attempts) + 5;
            game.setScore(game.getScore() + newScore);

        } else if (!currentRound.isOver() && allFeedback.size() >= 5) {
            game.changeState(new InactiveState());
            game.setGameStatus(ELIMINATED);
        }
    }
}
