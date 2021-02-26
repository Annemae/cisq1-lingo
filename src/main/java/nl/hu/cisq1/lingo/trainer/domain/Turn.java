package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Turn {
    private final Feedback feedback;
    private final Hint hint;

    public Turn(Feedback feedback, Hint hint) {
        this.feedback = feedback;
        this.hint = hint;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Hint getHint() {
        return hint;
    }
}
