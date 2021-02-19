package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Hint {
    private final List<Character> previousHint;
    private final List<Character> wordToGuess = new ArrayList<>();
    private final List<Mark> marks;

    private final List<Character> hint = new ArrayList<>();

    public Hint(List<Character> previousHint, String wordToGuess, List<Mark> marks) {
        this.previousHint = previousHint;
        this.marks = marks;
        for(char character : wordToGuess.toCharArray()) { //Naar WORD!!!
            this.wordToGuess.add(character);
        }

        this.calculateHint();
    }

    private List<Character> calculateHint() {
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

        return hint;
    }

    public List<Character> getHint() {
        return this.hint;
    }
}
