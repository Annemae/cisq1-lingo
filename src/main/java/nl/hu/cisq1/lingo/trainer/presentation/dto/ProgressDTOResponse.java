package nl.hu.cisq1.lingo.trainer.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Hint;

import java.io.Serializable;
import java.util.UUID;

public class ProgressDTOResponse implements Serializable {
    private int score;
    private Feedback feedback;
    private Hint hint;
    private UUID id;

    public ProgressDTOResponse(UUID id, int score, Feedback feedback, Hint hint) {
        this.id = id;
        this.score = score;
        this.feedback = feedback;
        this.hint = hint;
    }

    public UUID getId() {
        return this.id;
    }

    public int getScore() {
        return score;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Hint getHint() {
        return hint;
    }
}
