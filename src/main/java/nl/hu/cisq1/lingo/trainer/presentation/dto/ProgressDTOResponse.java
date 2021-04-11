package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.trainer.domain.game.GameStatus;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

public class ProgressDTOResponse extends RepresentationModel<ProgressDTOResponse> {
    private final UUID id;
    private final GameStatus gameStatus;
    private final int score;
    private final int amountOfGuesses;
    private final List<Mark> feedback;
    private final Hint hint;


    public ProgressDTOResponse(UUID id, GameStatus gameStatus, int score, int amountOfGuesses, List<Mark> feedback, Hint hint) {
        this.id = id;
        this.gameStatus = gameStatus;
        this.score = score;
        this.amountOfGuesses = amountOfGuesses;
        this.feedback = feedback;
        this.hint = hint;
    }

    public UUID getId() { return this.id; }

    public GameStatus getGameStatus() { return gameStatus; }

    public int getScore() { return score; }

    public int getAmountOfGuesses() { return amountOfGuesses; }

    public List<Mark> getFeedback() { return feedback; }

    public Hint getHint() { return hint; }
}
