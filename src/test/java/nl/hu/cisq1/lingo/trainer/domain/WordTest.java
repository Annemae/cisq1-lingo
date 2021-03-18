package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordTest {

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Word expected = new Word("WORD"); //WHEN
        Word actual = Word.of("WORD");

        assertEquals(expected.hashCode(), actual.hashCode()); //THEN
        assertEquals(expected, actual);
        assertEquals(actual, actual);
    }
}
