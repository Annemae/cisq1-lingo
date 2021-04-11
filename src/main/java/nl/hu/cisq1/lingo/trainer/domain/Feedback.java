package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_id")
    private UUID id;

    @ElementCollection
    private final List<Character> attemptCharacters = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Word wordToGuess;

    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private final List<Mark> marks = new ArrayList<>();

    public Feedback() { }
    public Feedback(Word wordToGuess) {
        this.wordToGuess = wordToGuess;

        calculateInitialFeedback();
    }
    public Feedback(String attempt, Word wordToGuess) {
        for (char character : attempt.toCharArray()) {
            this.attemptCharacters.add(character);
        }

        this.wordToGuess = wordToGuess;

        calculateMarks();
    }
    public static Feedback of(String attempt, Word wordToGuess) { return new Feedback(attempt, wordToGuess); }

    public boolean isWordGuessed() { return this.marks.stream().allMatch(mark -> mark == CORRECT); }

    public boolean isGuessValid() {
        return this.marks.stream().noneMatch(mark -> mark == INVALID);
    }

    private void calculateMarks() {
        List<Character> wordToGuessCharacters = this.wordToGuess.getWordCharacters();
        List<Character> absentCharacters = new ArrayList<>();

        if ((this.wordToGuess.getLength() != this.attemptCharacters.size()) ||
                (!wordToGuessCharacters.get(0).equals(this.attemptCharacters.get(0)))) {
            this.attemptCharacters.forEach(character -> this.marks.add(INVALID));
        } else {
            for (int i = 0; i < this.attemptCharacters.size(); i++) {
                Character character = this.attemptCharacters.get(i);
                if (character.equals(wordToGuessCharacters.get(i))) {
                    this.marks.add(CORRECT);
                    wordToGuessCharacters.set(i, '_');
                } else {
                    this.marks.add(ABSENT);
                    absentCharacters.add(character);
                }
            }
        }

        for (Character character : this.attemptCharacters) {
            if(absentCharacters.contains(character) && wordToGuessCharacters.contains(character)){
                this.marks.set(this.attemptCharacters.indexOf(character), PRESENT);
                absentCharacters.remove(character);
            }
        }
    }

    private void calculateInitialFeedback() {
        this.marks.add(CORRECT);
        for (int i = 1; i < wordToGuess.getWordCharacters().size(); i++) {
            this.marks.add(ABSENT);
        }
    }

    public List<Mark> getMarks() { return this.marks; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attemptCharacters, feedback.attemptCharacters) &&
                Objects.equals(wordToGuess, feedback.wordToGuess) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attemptCharacters, wordToGuess, marks);
    }
}
