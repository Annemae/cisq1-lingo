package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private final Word wordToGuess;
    private final List<Feedback> attempts;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.attempts = new ArrayList<>();
    }

    public void takeGuess(String attempt) {
        Feedback newFeedback = new Feedback(attempt, wordToGuess);

        this.attempts.add(newFeedback);
    }

    public Hint giveHint() {
        return Hint.of(attempts, wordToGuess);
    }

    public boolean isOver() {
        Feedback lastFeedback = getRecentFeedback();
        return lastFeedback.isWordGuessed();
    }

    //ATTEMPTS
    public Feedback getRecentFeedback() {
        if(amountOfGuesses() > 0) {
            return this.attempts.get(this.attempts.size() - 1);
        } else throw new IllegalStateException("Take a guess first, before asking for feedback.");
    }

    public List<Feedback> getAllFeedback() {
        return attempts;
    }

    public int amountOfGuesses() {
        return attempts.size();
    }
}
