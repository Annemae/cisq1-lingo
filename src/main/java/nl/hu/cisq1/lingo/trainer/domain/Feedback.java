package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback {
    private final String attempt;
    private final Word wordToGuess;

    private final List<Mark> marks;

    public Feedback(String attempt, Word wordToGuess) {
        this.attempt = attempt;
        this.wordToGuess = wordToGuess;
        marks = new ArrayList<>();
        calculateMarks(attempt, wordToGuess);
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
        }
        throw new InvalidGuessException("Guess is invalid, because guess is not the right length.");
    }

    private void calculateMarks(String attempt, Word wordToGuess) { //todo shorter and present
        List<Character> attemptCharacters = new ArrayList<>();
        List<Character> wordToGuessCharacters = wordToGuess.getWordCharacters();

        for (char character : attempt.toCharArray()) {
            attemptCharacters.add(character);
        }

        if ((wordToGuess.getLength() != attemptCharacters.size()) ||
        (!wordToGuessCharacters.get(0).equals(attemptCharacters.get(0))))
            attemptCharacters.forEach(character -> marks.add(INVALID));
        else {
            for(int i = 0; i < attemptCharacters.size(); i++) {
                if(i == 0) {
                    marks.add(CORRECT);
                } else {
                    if (attemptCharacters.get(i).equals(wordToGuessCharacters.get(i))) {
                        marks.add(CORRECT);
                    } else if (!attemptCharacters.get(i).equals(wordToGuessCharacters.get(i)) && wordToGuessCharacters.contains(attemptCharacters.get(i))) {
                        marks.add(PRESENT);
                    } else marks.add(ABSENT);
                }
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
}
