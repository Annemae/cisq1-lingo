package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Word { //todo equals and static??
    public List<Character> word = new ArrayList<>();
    public int length;

    public Word(String word) {
        for(char character : word.toCharArray()) {
            this.word.add(character);
        }
        this.length = this.word.size();
    }

    public static Word of(String word) {
        return new Word(word);
    }

    public List<Character> getWord() {
        return this.word;
    }

    public int getLength() {
        return length;
    }
}
