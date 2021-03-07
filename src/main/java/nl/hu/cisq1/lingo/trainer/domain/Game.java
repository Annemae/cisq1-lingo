package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.GameStatus.*;

public class Game {
    private List<Round> rounds;
    private int score;
    private GameStatus gameStatus;
    private State state;

    public Game() {
        rounds = new ArrayList<>();
        score = 0;
        gameStatus = PLAYING;
        state = new ActiveState(this, new DefaultScoreStrategy());
    }

    public void changeState(State state) {
        this.state = state;
    }

    public Round createNewRound(String wordToGuess) {
        return state.createNewRound(Word.of(wordToGuess));
    }

    public void takeGuess(String attempt) {
        state.takeGuess(attempt);
    }

    public Progress showProgress() {
        Round round = getCurrentRound();
        Feedback feedback = round.getLastFeedback();
        Hint hint = round.giveHint();

        return new Progress(score, feedback, hint);
    }

    //ROUND
    public void addRound(Round round) {
        rounds.add(round);
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    //SCORE
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
