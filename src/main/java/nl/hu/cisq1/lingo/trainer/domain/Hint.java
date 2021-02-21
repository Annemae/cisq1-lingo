package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Hint { //todo equalas etc, and static?
    private final List<Character> previousHint;
    private final List<Character> wordToGuess;
    private final List<Mark> marks;

    private final List<Character> hint = new ArrayList<>();

    private Hint(List<Character> previousHint, Word wordToGuess, List<Mark> marks) {
        if((marks.size() != previousHint.size()) || (marks.size() != wordToGuess.getWord().size())) {
            throw new InvalidHintException("The hint is not valid.");
        } else {
            this.previousHint = previousHint;
            this.marks = marks;
            this.wordToGuess = wordToGuess.getWord();

            this.calculateHint();
        }
    }

    public static Hint of(List<Character> previousHint, Word wordToGuess, List<Mark> marks) {
        return new Hint(previousHint, wordToGuess, marks);
    }

    private void calculateHint() {
        int count = 0;
        for(Character character : previousHint) {
            if(character == '.') {
                if(marks.get(count) == CORRECT) {
                    hint.add(wordToGuess.get(count));
                } else {
                    hint.add('.');
                }
            } else {
                hint.add(character);
            }
            count += 1;
        }
    }

    public List<Character> getHint() {
        return this.hint;
    }
}
