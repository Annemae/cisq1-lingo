package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback {
    private final List<Character> attempt;
    private final Word wordToGuess;

    private final List<Mark> marks;

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

        if ((wordToGuess.getLength() != attempt.size()) ||
                (!wordToGuessCharacters.get(0).equals(attempt.get(0))))
            attempt.forEach(character -> marks.add(INVALID));
        else {
            for(int i = 0; i < attempt.size(); i++) {
                Character attemptCharacter = attempt.get(i);
                Character wordToGuessCharacter = wordToGuessCharacters.get(i);

                if (attemptCharacter.equals(wordToGuessCharacter)) {
                    marks.add(CORRECT);
                } else if (wordToGuessCharacters.contains(attemptCharacter)) {
                    calculatePresent(wordToGuessCharacters, attemptCharacter);
                } else marks.add(ABSENT);
            }
        }
    }

    private void calculatePresent(List<Character> wordToGuessCharacters, Character character) {
        long amountCharactersInAttempt = this.attempt.stream().filter(character::equals).count();
        long amountCharactersInWordToGuess = wordToGuessCharacters.stream().filter(character::equals).count();
        long amountPresentInMarks = this.marks.stream().filter(mark -> mark == PRESENT).count();

        if (amountPresentInMarks == amountCharactersInAttempt ||
                amountCharactersInAttempt < amountPresentInMarks ||
                amountCharactersInAttempt == 1 && amountCharactersInWordToGuess == 1) {
            this.marks.add(PRESENT);
        } else {
            this.marks.add(ABSENT);
        }
    } //TODO ???

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
