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
    private List<Feedback> attempts;

    public Round() {}
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


    public Word getWordToGuess() {
        return wordToGuess;
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
