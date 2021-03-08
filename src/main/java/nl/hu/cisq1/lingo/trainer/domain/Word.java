package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Word {
    private final List<Character> wordCharacters = new ArrayList<>();
    private final int length;

    public Word(String wordCharacters) {
        for(char character : wordCharacters.toCharArray()) {
            this.wordCharacters.add(character);
        }
        this.length = this.wordCharacters.size();
    }

    public static Word of(String word) {
        return new Word(word);
    }

    public List<Character> getWordCharacters() {
        return this.wordCharacters;
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
                Objects.equals(wordCharacters, word1.wordCharacters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordCharacters, length);
    }
}
