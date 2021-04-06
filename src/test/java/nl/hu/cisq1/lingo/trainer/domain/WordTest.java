package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordTest {
    static Stream<Arguments> provideEqualsExamples() {
        Word word = new Word("BREAD");
        return Stream.of(
                Arguments.of(word,
                        word,
                        true),
                Arguments.of(word,
                        null,
                        false),
                Arguments.of(word,
                        new Word("BETTER"),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideEqualsExamples")
    @DisplayName("equals works correctly")
    void equalsWorks(Word wordOne, Word wordTwo, boolean isEqual) {
        assertEquals(wordOne.equals(wordTwo), isEqual);
    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Word expected = new Word("WORD");
        Word actual = Word.of("WORD");

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}
