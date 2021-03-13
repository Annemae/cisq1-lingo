package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.State;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private UUID id;

    @OneToMany(targetEntity = Round.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "round_id")
    private final List<Round> rounds;

    @Column
    private int score;

    @Column
    private GameStatus gameStatus;

    @Lob
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

    public void createNewRound(String wordToGuess) {
        state.createNewRound(Word.of(wordToGuess));
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

    public UUID getId() {
        return id;
    }
}
