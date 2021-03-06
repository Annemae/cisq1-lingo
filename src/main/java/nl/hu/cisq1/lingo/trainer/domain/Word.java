package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "word_id")
    private UUID id;

    @Column
    private String wordToGuess;

    public Word() { }
    public Word(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }
    public static Word of(String word) { return new Word(word); }

    public List<Character> getWordCharacters() {
        List<Character> wordCharacters = new ArrayList<>();

        for (char character : wordToGuess.toCharArray()) {
            wordCharacters.add(character);
        }

        return wordCharacters;
    }

    public int getLength() { return this.getWordCharacters().size(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(wordToGuess, word.wordToGuess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordToGuess);
    }
}
