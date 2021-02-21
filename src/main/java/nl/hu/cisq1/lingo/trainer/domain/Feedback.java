package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback {
    private final String attempt;
    private final Word wordToGuess;

    private final List<Mark> marks = new ArrayList<>();

    private Feedback(String attempt, Word wordToGuess) {
        this.attempt = attempt;
        this.wordToGuess = wordToGuess;

        this.calculateFeedback();
    }

    public static Feedback of(String attempt, Word word) {
        return new Feedback(attempt, word);
    }

    private void calculateFeedback() {
        List<Character> wordToGuess = this.wordToGuess.getWord();
        List<Character> attempt = new ArrayList<>();
        for (char character : this.attempt.toCharArray()) {
            attempt.add(character);
        }

        if (wordToGuess.size() != attempt.size()) {
            attempt.forEach(character -> {
                marks.add(INVALID);
            });
        } else {
            int count = 0;
            for (Character character : attempt) {
                if (character == wordToGuess.get(count)) {
                    marks.add(CORRECT);
                } else if (character != wordToGuess.get(count) && wordToGuess.contains(character)) {
                    marks.add(PRESENT);
                } else {
                    marks.add(ABSENT);
                }
                count += 1;
            }
        }
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

    public List<Character> giveHint(List<Character> previousHint, Word wordToGuess, List<Mark> marks) {
        return new Hint(previousHint, wordToGuess, marks).getHint();
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
