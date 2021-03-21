package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_id")
    private UUID id;

    @ElementCollection
    private List<Character> attemptCharacters;

    @OneToOne(cascade = CascadeType.ALL)
    private Word wordToGuess;

    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;

    public Feedback() {
    }
    public Feedback(String attempt, Word wordToGuess) {
        this.attemptCharacters = new ArrayList<>();
        for (char character : attempt.toCharArray()) {
            this.attemptCharacters.add(character);
        }

        this.wordToGuess = wordToGuess;

        this.marks = new ArrayList<>();
        calculateMarks();
    }

    public static Feedback of(String attempt, Word wordToGuess) {
        return new Feedback(attempt, wordToGuess);
    }

    //BOOLEANS
    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == CORRECT);
    }

    public boolean isGuessValid() {
        if (marks.stream().noneMatch(mark -> mark == INVALID)) {
            return true;
        } else throw new InvalidGuessException("Guess is invalid, because guess is not the right length.");
    }

    //CALCULATOR
    private void calculateMarks() {
        List<Character> wordToGuessCharacters = wordToGuess.getWordCharacters();
        List<Character> absentCharacters = new ArrayList<>();

        if ((wordToGuess.getLength() != this.attemptCharacters.size()) ||
                (!wordToGuessCharacters.get(0).equals(this.attemptCharacters.get(0))))
            this.attemptCharacters.forEach(character -> marks.add(INVALID));
        else {
            int index = 0;
            for (Character character : this.attemptCharacters) {

                if (character.equals(wordToGuessCharacters.get(index))) {
                    this.marks.add(CORRECT);
                    wordToGuessCharacters.set(index, '_');
                }else {
                    absentCharacters.add(character);
                    this.marks.add(ABSENT);
                }
                index += 1;
            }
        }

        for (Character character : this.attemptCharacters) {
            if(absentCharacters.contains(character) && wordToGuessCharacters.contains(character)){
                absentCharacters.remove(character);
                this.marks.set(this.attemptCharacters.indexOf(character), PRESENT);
            }
        }
    }


    //GETTER
    public List<Mark> getMarks() {
        return marks;
    }

    //EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) && Objects.equals(attemptCharacters, feedback.attemptCharacters) && Objects.equals(wordToGuess, feedback.wordToGuess) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attemptCharacters, wordToGuess, marks);
    }
}
