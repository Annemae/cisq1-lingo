package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private final Word wordToGuess;
    private List<Feedback> feedback;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.feedback = new ArrayList<>();
        feedback.add(Feedback.of("", wordToGuess, Hint.calculateFirstHint(wordToGuess)));
    }

    public Feedback takeGuess(String attempt) {
        Feedback newFeedback = new Feedback(attempt, wordToGuess, getLastFeedback().getHint());

        if(newFeedback.isGuessValid()) {
            this.feedback.add(newFeedback);
        } else {
            throw new InvalidGuessException("Guess is not valid.");
        }

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

    //FEEDBACK
    public Feedback getLastFeedback() {
        return this.feedback.get(this.feedback.size() - 1);
    }

    public List<Feedback> getAllFeedback() {
        return feedback;
    }
}
