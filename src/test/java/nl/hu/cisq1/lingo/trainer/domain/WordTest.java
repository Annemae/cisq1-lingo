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
    private static Stream<Arguments> provideTrueEqualsExamples() {
        Word word = new Word("BREAD");
        return Stream.of(
                Arguments.of(true,
                        new Word("BREAD"),
                        new Word("BREAD")),
                Arguments.of(true,
                        word,
                        word),
                Arguments.of(false,
                        word,
                        null),
                Arguments.of(false,
                        word,
                        new Word("BETTER"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideTrueEqualsExamples")
    @DisplayName("equals works correctly")
    void equalsWorks(boolean expectedIsEqual, Word wordOne, Word wordTwo) {
        assertEquals(expectedIsEqual, wordOne.equals(wordTwo));
    }

    @Test
    @DisplayName("gives back correct word characters")
    void wordCharactersWorks() {
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
