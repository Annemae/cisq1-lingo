package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    private static Stream<Arguments> provideEqualsExamples() {
        return Stream.of(
                Arguments.of(true,
                        new Word("BREAD"),
                        new Word("BREAD")),
                Arguments.of(false,
                        new Word("BREAD"),
                        null),
                Arguments.of(false,
                        new Word("BREAD"),
                        new Word("BETTER")),
                Arguments.of(false,
                        new Word("BREAD"),
                        new Feedback("EQUALS", Word.of("EQUALS")))
        );
    }

    @ParameterizedTest
    @MethodSource("provideEqualsExamples")
    @DisplayName("equals works correctly")
    void equalsWorks(boolean expectedIsEqual, Word word, Object object) {
        assertEquals(expectedIsEqual, word.equals(object));
    }

    @Test
    @DisplayName("gives back correct word characters")
    void giveWordCharactersWorks() {
        Word word = Word.of("APPLE");

        assertEquals(List.of('A', 'P', 'P', 'L', 'E'), word.getWordCharacters());
    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Word expected = new Word("WORD");
        Word actual = Word.of("WORD");

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}
