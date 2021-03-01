package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Word {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return length == word1.length &&
                Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, length);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word=" + word +
                ", length=" + length +
                '}';
    }
}
