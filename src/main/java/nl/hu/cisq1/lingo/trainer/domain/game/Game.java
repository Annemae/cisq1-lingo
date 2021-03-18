package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.game.state.ActiveState;
import nl.hu.cisq1.lingo.trainer.domain.game.state.State;
import nl.hu.cisq1.lingo.trainer.domain.game.state.StateConverter;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.StrategyConverter;
import nl.hu.cisq1.lingo.trainer.domain.game.strategy.WordLengthStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static nl.hu.cisq1.lingo.trainer.domain.game.GameStatus.PLAYING;

@Entity
@Table(name = "game")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private UUID id;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Round> rounds = new ArrayList<>();

    @Column
    private int score;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Convert(converter = StateConverter.class)
    private State state;

    @Convert(converter = StrategyConverter.class)
    private WordLengthStrategy wordLengthStrategy;

    public Game() {}
    public Game(WordLengthStrategy wordLengthStrategy) {
        score = 0;
        gameStatus = PLAYING;
        state = new ActiveState();
        this.wordLengthStrategy = wordLengthStrategy;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void createNewRound(String wordToGuess) {
        state.createNewRound(Word.of(wordToGuess), this);
    }

    public void takeGuess(String attempt) {
        state.takeGuess(attempt, this);
    }

    public GameProgress createGameProgress() {
        Round round = getCurrentRound();
        Feedback feedback = round.getRecentFeedback().orElse(null);
        Hint hint = round.giveHint();

        return new GameProgress(id, score, gameStatus, feedback, hint, rounds);
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

    //GETTER
    public UUID getId() {
        return id;
    }

    public WordLengthStrategy getWordLengthStrategy() {
        return wordLengthStrategy;
    }
}
