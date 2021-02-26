package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private final Word wordToGuess;
    private List<Turn> turns;
    private final Hint firstHint;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.turns = new ArrayList<>();

        this.firstHint = Hint.of(null, wordToGuess, null); //todo anders?
    }

    public Turn takeGuess(String attempt) {
        Feedback feedback = new Feedback(attempt, this.wordToGuess);

        if(feedback.isGuessValid()) {
            Hint hint;
            if (this.turns.size() == 0) {
                hint = Hint.of(this.firstHint, this.wordToGuess, feedback.getMarks());
            } else {
                hint = Hint.of(this.getLastTurn().getHint(), this.wordToGuess, feedback.getMarks());
            }
            this.turns.add(new Turn(feedback, hint));
        } else {
            throw new InvalidGuessException("Guess is not valid.");
        }
        return this.getLastTurn();
    }

    public boolean isOver() {
        Feedback lastFeedback = getLastTurn().getFeedback();
        if(this.turns.size() < 6) {
            return lastFeedback.isWordGuessed();
        } else {
            return true;
        }
    }

    public Hint getFirstHint() {
        return this.firstHint;
    }

    public Turn getLastTurn() {
        return this.turns.get(this.turns.size() - 1);
    }
}
