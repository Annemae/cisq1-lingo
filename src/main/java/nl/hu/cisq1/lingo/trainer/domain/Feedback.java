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

    @Lob
    private List<Character> attempt;

    @OneToOne
    private Word wordToGuess;

    @Lob
    private List<Mark> marks;

    public Feedback() {}
    public Feedback(String attempt, Word wordToGuess) {
        this.attempt = new ArrayList<>();
        for (char character : attempt.toCharArray()) {
            this.attempt.add(character);
        }
        this.wordToGuess = wordToGuess;

        marks = new ArrayList<>();
        calculateMarks();
    }


    public static Feedback of(String attempt, Word wordToGuess) {
        return new Feedback(attempt, wordToGuess);
    }


    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == CORRECT);
    }

    public boolean isGuessValid() {
        if (marks.stream().noneMatch(mark -> mark == INVALID)) {
            return true;
        } else throw new InvalidGuessException("Guess is invalid, because guess is not the right length.");
    }

    //CALCULATORS
    private void calculateMarks() {
        List<Character> wordToGuessCharacters = wordToGuess.getWordCharacters();
        List<Character> absentCharacters = new ArrayList<>();

        if ((wordToGuess.getLength() != attempt.size()) ||
                (!wordToGuessCharacters.get(0).equals(attempt.get(0))))
            attempt.forEach(character -> marks.add(INVALID));
        else {
            for(int i = 0; i < attempt.size(); i++) {
                Character attemptCharacter = attempt.get(i);
                Character wordToGuessCharacter = wordToGuessCharacters.get(i);

                if (attemptCharacter.equals(wordToGuessCharacter)) {
                    marks.add(CORRECT);
                } else {
                    absentCharacters.add(attemptCharacter);
                    marks.add(ABSENT);
                }
            }
        }
        recalculatePresent(wordToGuessCharacters, absentCharacters);
    }

    private void recalculatePresent(List<Character> wordToGuessCharacters, List<Character> absentCharacters) {
        for (Character character : wordToGuessCharacters) {
            int attemptPosition = attempt.indexOf(character);
            if (attemptPosition != -1) {
                long countListAttempt = this.attempt.stream().filter(character::equals).count();
                long countListWordToBeGuessed = wordToGuessCharacters.stream().filter(character::equals).count();
                if (absentCharacters.contains(character) &&
                        this.marks.get(attemptPosition) == ABSENT &&
                        countListAttempt <= countListWordToBeGuessed
                ) {
                    absentCharacters.remove(character);
                    this.marks.set(attempt.indexOf(character), PRESENT);
                }
            }
        }
    }

    //GETTERS
    public List<Mark> getMarks() {
        return marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(wordToGuess, feedback.wordToGuess) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, wordToGuess, marks);
    }
}
