package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public Round() {
    }
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
        Optional<Feedback> lastFeedbackOptional = getRecentFeedback();
        if(lastFeedbackOptional.isPresent()) {
            Feedback lastFeedback = lastFeedbackOptional.get();
            return lastFeedback.isWordGuessed();
        } else return false;
    }


    //GETTERS
    public Word getWordToGuess() {
        return wordToGuess;
    }

    public Optional<Feedback> getRecentFeedback() {
        if(attempts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.attempts.get(this.attempts.size() - 1));
    }

    public List<Feedback> getAllFeedback() {
        return attempts;
    }

    public int amountOfGuesses() {
        return attempts.size();
    }
}
