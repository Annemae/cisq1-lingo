package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Hint { //todo previoushint optional
    private final List<Character> hint;

    public Hint(List<Character> characters) {
       this.hint = characters;
    }

    public static Hint of(Hint previousHint, Word wordToGuess, List<Mark> marks) {
        if((marks.size() != previousHint.getHint().size()) || (marks.size() != wordToGuess.getWord().size())) {
            throw new InvalidHintException("The hint is not valid.");
        } else {
            return new Hint(calculateHint(previousHint, wordToGuess, marks));
        }
    }

    private static List<Character> calculateHint(Hint previousHint, Word wordToGuess, List<Mark> marks) {
        List<Character> characters = new ArrayList<>();
        int count = 0;
        for(Character character : previousHint.getHint()) {
            if(character == '.') {
                if(marks.get(count) == CORRECT) {
                    characters.add(wordToGuess.getWord().get(count));
                } else {
                    characters.add('.');
                }
            } else {
                characters.add(character);
            }
            count += 1;
        }
        return characters;
    }

    public List<Character> getHint() {
        return this.hint;
    }
}
