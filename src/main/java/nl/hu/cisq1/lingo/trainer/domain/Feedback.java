package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback {
    private final String attempt;
    private final Word wordToGuess;

    private final List<Mark> marks;
    private final Hint hint;

    public Feedback(String attempt, Word wordToGuess, Hint previousHint) {
        this.attempt = attempt;
        this.wordToGuess = wordToGuess;
        marks = new ArrayList<>();

        if(attempt.equals("")) {
            this.calculateFirstMarks(wordToGuess);
        } else {
            this.calculateMarks(attempt, wordToGuess);
        }
        hint = Hint.of(previousHint, wordToGuess, this.getMarks());
    }

    public static Feedback of(String attempt, Word wordToGuess, Hint previousHint) {
        return new Feedback(attempt, wordToGuess, previousHint);
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == CORRECT);
    }

    public boolean isGuessValid() {
        return marks.stream().noneMatch(mark -> mark == INVALID);
    }

    private void calculateFirstMarks(Word wordToGuess) {
        List<Character> wordToGuessCharacters = wordToGuess.getWord();
        for(int i = 0; i < wordToGuessCharacters.size(); i++) {
            if(i == 0) {
                marks.add(CORRECT);
            } else {
                marks.add(ABSENT);
            }
        }
    }

    private void calculateMarks(String attempt, Word wordToGuess) {
        List<Character> attemptCharacters = new ArrayList<>();
        List<Character> wordToGuessCharacters = wordToGuess.getWord();
        for (char character : attempt.toCharArray()) {
            attemptCharacters.add(character);
        }

        if (wordToGuess.getLength() != attemptCharacters.size()) {//todo of geen string is  em verkeerde beginletter
            attemptCharacters.forEach(character -> {
                marks.add(INVALID);
            });
        } else {
            for(int i = 0; i < attemptCharacters.size(); i++) {
                if(i == 0) {
                    marks.add(CORRECT);
                } else {
                    if (attemptCharacters.get(i) == wordToGuessCharacters.get(i)) {
                        marks.add(CORRECT);
                    } else if (attemptCharacters.get(i) != wordToGuessCharacters.get(i) && wordToGuessCharacters.contains(attemptCharacters.get(i))) {
                        marks.add(PRESENT); //todo present ???
                    } else {
                        marks.add(ABSENT);
                    }
                }
            }
        }
    }


    public List<Mark> getMarks() {
        return marks;
    }

    public Hint getHint() {
        return hint;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(wordToGuess, feedback.wordToGuess) &&
                Objects.equals(marks, feedback.marks) &&
                Objects.equals(hint, feedback.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, wordToGuess, marks, hint);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", wordToGuess=" + wordToGuess +
                ", marks=" + marks +
                ", hint=" + hint +
                '}';
    }
}
