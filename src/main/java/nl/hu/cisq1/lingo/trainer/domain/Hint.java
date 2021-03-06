package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.CORRECT;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.INVALID;

public class Hint {
    private final List<Character> hintCharacterList = new ArrayList<>();

    public Hint(List<Feedback> feedbackList, Word wordToGuess) {
        calculateHint(feedbackList, wordToGuess);
    }
    public static Hint of(List<Feedback> feedbackList, Word wordToGuess) {
        return new Hint(feedbackList, wordToGuess);
    }

    private void calculateHint(List<Feedback> feedbackList, Word wordToGuess) {
        calculateInitialCharacters(wordToGuess);

        List<Character> wordCharacters = wordToGuess.getWordCharacters();

        for (Feedback feedback : feedbackList) {
            List<Mark> marks = feedback.getMarks();
            if (!marks.contains(INVALID)) {
                for (int i = 0; i < wordToGuess.getLength(); i++) {
                    if (marks.get(i) == CORRECT) {
                        this.hintCharacterList.set(i, wordCharacters.get(i));
                    }
                }

            }
        }
    }

    private void calculateInitialCharacters(Word wordToGuess) {
        List<Character> wordArray = wordToGuess.getWordCharacters();

        this.hintCharacterList.add(wordArray.get(0));
        for (int i = 1; i < wordArray.size(); i++) {
            this.hintCharacterList.add('.');
        }
    }

    public List<Character> getHintCharacters() { return this.hintCharacterList; }

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
