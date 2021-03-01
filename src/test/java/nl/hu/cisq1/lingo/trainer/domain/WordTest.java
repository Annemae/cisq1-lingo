package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordTest {

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Word expected = new Word("WORD");
        Word actual = Word.of("WORD");

        assertEquals(expected.hashCode(), actual.hashCode());
        assertEquals(expected, actual);
    }
}
