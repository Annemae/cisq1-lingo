package nl.hu.cisq1.lingo.trainer.domain.game;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Hint;

public class Progress {
    private int score;
    private Feedback feedback;
    private Hint hint;

    public Progress(int score, Feedback feedback, Hint hint) {
        this.score = score;
        this.feedback = feedback;
        this.hint = hint;
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
