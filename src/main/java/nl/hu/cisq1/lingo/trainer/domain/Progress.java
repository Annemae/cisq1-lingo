package nl.hu.cisq1.lingo.trainer.domain;

public class Progress {
    private int score;
    private Feedback feedback;
    private Hint hint;

    public Progress(int score, Feedback feedback, Hint hint) {
        this.score = score;
        this.feedback = feedback;
        this.hint = hint;
    }
}
