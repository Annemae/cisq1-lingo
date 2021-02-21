package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Word {
    public List<Character> word = new ArrayList<>();

    public Word(String word) {
        for(char character : word.toCharArray()) {
            this.word.add(character);
        }
    }

    public List<Character> getWord() {
        return this.word;
    }
}
