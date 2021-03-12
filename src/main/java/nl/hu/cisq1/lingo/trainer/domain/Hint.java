package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Hint {
    private final List<Character> hintCharacterList;

    public Hint(List<Feedback> feedbackList, Word wordToGuess) {
        hintCharacterList = calculateHint(feedbackList, wordToGuess);
    }

    public static Hint of(List<Feedback> feedbackList, Word wordToGuess) {
        return new Hint(feedbackList, wordToGuess);
    }

    private List<Character> calculateHint(List<Feedback> feedbackList, Word wordToGuess) {
        List<Character> characters = calculateInitialCharacters(wordToGuess);
        List<Character> wordCharacters = wordToGuess.getWordCharacters();

        for (Feedback feedback : feedbackList) {
            List<Mark> marks = feedback.getMarks();
            if (!marks.contains(INVALID)) {
                for (int i = 0; i < wordToGuess.getLength(); i++) {
                    if (marks.get(i) == CORRECT) {
                        characters.set(i, wordCharacters.get(i));
                    }
                }

            }
        }

        return characters;
    }

    private List<Character> calculateInitialCharacters(Word wordToGuess) {
        List<Character> firstHint = new ArrayList<>();
        List<Character> wordArray = wordToGuess.getWordCharacters();

        for(int i = 0; i < wordArray.size(); i++) {
            if(i == 0) {
                firstHint.add(wordArray.get(0));
            } else firstHint.add('.');
        }
        return firstHint;
    }

    public List<Character> getHintCharacters() {
        return hintCharacterList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return Objects.equals(hintCharacterList, hint.hintCharacterList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hintCharacterList);
    }
}
