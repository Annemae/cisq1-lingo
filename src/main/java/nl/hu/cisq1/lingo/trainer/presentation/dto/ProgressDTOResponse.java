package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.trainer.domain.game.GameStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ProgressDTOResponse implements Serializable {
    private UUID id;
    private GameStatus gameStatus;
    private int score;
    private List<Mark> feedback;
    private Hint hint;


    public ProgressDTOResponse(UUID id, GameStatus gameStatus, int score, List<Mark> feedback, Hint hint) {
        this.id = id;
        this.gameStatus = gameStatus;
        this.score = score;
        this.feedback = feedback;
        this.hint = hint;
    }

    public UUID getId() {
        return this.id;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getScore() {
        return score;
    }

    public List<Mark> getFeedback() {
        return feedback;
    }

    public Hint getHint() {
        return hint;
    }
}
