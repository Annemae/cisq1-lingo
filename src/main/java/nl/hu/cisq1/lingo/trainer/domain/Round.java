package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private final Word wordToGuess;
    private List<Feedback> feedback;
    private final Hint firstHint;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.feedback = new ArrayList<>();

        this.firstHint = Hint.of(null, wordToGuess, null); //todo anders?
    }

    public Feedback takeGuess(String attempt) {
        Feedback newFeedback;
        if (this.feedback.size() == 0) {
            newFeedback = new Feedback(attempt, wordToGuess, firstHint);
        } else {
            newFeedback = new Feedback(attempt, wordToGuess, getLastFeedback().getHint());
        }
        this.feedback.add(newFeedback);

        return this.getLastFeedback();
    }

    public boolean isOver() {
        Feedback lastFeedback = getLastFeedback();
        if(this.feedback.size() < 6) {
            return lastFeedback.isWordGuessed();
        } else {
            return true;
        }
    }

    public Hint getFirstHint() {
        return this.firstHint;
    }

    public Feedback getLastFeedback() {
        return this.feedback.get(this.feedback.size() - 1);
    }
}
