package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "round")
public class Round implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "round_id")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private Word wordToGuess;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Feedback> attempts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Feedback initialFeedback;

    public Round() {
    }
    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.initialFeedback = new Feedback(wordToGuess);
    }

    public void takeGuess(String attempt) {
        Feedback newFeedback = new Feedback(attempt, wordToGuess);

        if(!this.isOver()) { this.attempts.add(newFeedback); }
    }

    public Hint giveHint() { return Hint.of(attempts, wordToGuess); }


    public boolean isOver()  {
        if(this.attempts.size() >= 5) { return true; }

        Feedback lastFeedback = this.getLastFeedback();

        return lastFeedback.isWordGuessed();
    }


    public Word getWordToGuess() { return wordToGuess; }

    public Feedback getLastFeedback() {
        if(attempts.isEmpty()) {
            return initialFeedback;
        }
        return this.attempts.get(this.attempts.size() - 1);
    }

    public List<Feedback> getAllFeedback() { return attempts; }

    public int amountOfGuesses() { return attempts.size(); }
}
