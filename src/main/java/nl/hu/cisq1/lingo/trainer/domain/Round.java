package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;

public class Round {
    private Word wordToGuess;
    private List<Turn> turns;
    private Hint firstHint;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.turns = new ArrayList<>();

        this.firstHint = Hint.of(null, wordToGuess, null); //todo anders?
    }

    public Turn takeGuess(String attempt) {
        Feedback feedback = new Feedback(attempt, this.wordToGuess);

        if(this.turns.size() == 0) {
            this.turns.add(new Turn(feedback, this.firstHint));
        } else {
            Hint hint = Hint.of(this.getLastTurn().getHint(), this.wordToGuess, feedback.getMarks());
            this.turns.add(new Turn(feedback, hint));
        }
        return this.getLastTurn();
    }


    public Hint getFirstHint() {
        return this.firstHint;
    }

    public Turn getLastTurn() {
        return this.turns.get(this.turns.size() - 1);
    }
}
