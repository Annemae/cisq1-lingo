package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback { //Klasse die de marks genereerd.
    private final String attempt;
    private final Word wordToGuess;

    private final List<Mark> marks;

    public Feedback(String attempt, Word wordToGuess) {
        this.attempt = attempt;
        this.wordToGuess = wordToGuess;
        this.marks = new ArrayList<>();

        this.calculateMarks(attempt, wordToGuess);
    }

    public static Feedback of(String attempt, Word wordToGuess) {
        return new Feedback(attempt, wordToGuess);
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == CORRECT);
    }

    public boolean isGuessValid() {
        if(marks.stream().noneMatch(mark -> mark == INVALID)) {
            return true;
        }
        throw new InvalidFeedbackException("The guess is not valid.");
    }

    private void calculateMarks(String attempt, Word wordToGuess) {
        List<Character> attemptCharacters = new ArrayList<>();
        for (char character : attempt.toCharArray()) {
            attemptCharacters.add(character);
        }

        if (wordToGuess.getLength() != attemptCharacters.size()) {
            attemptCharacters.forEach(character -> {
                this.marks.add(INVALID);
            });
        } else {
            int count = 0;
            for (Character character : attemptCharacters) {
                if (character == wordToGuess.getWord().get(count)) {
                    this.marks.add(CORRECT);
                } else if (character != wordToGuess.getWord().get(count) && wordToGuess.getWord().contains(character)) { //todo korter
                    this.marks.add(PRESENT); //todo present ???
                } else {
                    this.marks.add(ABSENT);
                }
                count += 1;
            }
        }
    }


    public List<Mark> getMarks() {
        return marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(wordToGuess, feedback.wordToGuess) &&
                Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, wordToGuess, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", wordToGuess=" + wordToGuess +
                ", marks=" + marks +
                '}';
    }
}