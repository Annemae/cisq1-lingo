package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Hint {
    private final List<Character> hint;

    public Hint(List<Character> characters) {
        hint = characters;
    }

    public static Hint of(Hint previousHint, Word wordToGuess, List<Mark> marks) {
        if((marks.size() != previousHint.getHint().size()) || (marks.size() != wordToGuess.getLength())
                || marks.stream().anyMatch(mark -> mark == INVALID)) {
            throw new InvalidHintException("The hint can't be generated with given data.");
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

    public static Hint calculateFirstHint(Word wordToGuess) { //todo vragem
        List<Character> firstHint = new ArrayList<>();
        List<Character> wordArray = wordToGuess.getWord();

        for(int i = 0; i < wordArray.size(); i++) {
            if(i == 0) {
                firstHint.add(wordArray.get(0));
            } else {
                firstHint.add('.');
            }
        }
        return new Hint(firstHint);
    }

    public List<Character> getHint() {
        return hint;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint1 = (Hint) o;
        return Objects.equals(hint, hint1.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hint);
    }
}
