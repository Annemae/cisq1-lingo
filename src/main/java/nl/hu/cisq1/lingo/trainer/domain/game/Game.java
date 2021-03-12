package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.State;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;
//table en entity
public class Game {
    private final List<Round> rounds;
    private int score;
    private GameStatus gameStatus;
    private State state;

    public Game() {
        rounds = new ArrayList<>();
        score = 0;
        gameStatus = PLAYING;
        state = new ActiveState(this);
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
        Feedback feedback = round.getRecentFeedback();
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

    public List<Round> getRounds() {
        return this.rounds;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
