package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.util.*;

public class GameProgress {
    private final UUID id;
    private final int score;
    private final GameStatus gameStatus;
    private final Feedback feedback;
    private final Hint hint;
    private final List<Round> rounds;

    public GameProgress(UUID id, int score, GameStatus gameStatus, Feedback feedback, Hint hint, List<Round> rounds) {
        this.id = id;
        this.score = score;
        this.gameStatus = gameStatus;
        this.feedback = feedback;
        this.hint = hint;
        this.rounds = rounds;
    }

    public UUID getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Hint getHint() {
        return hint;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
