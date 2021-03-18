package nl.hu.cisq1.lingo.trainer.domain.game.strategy;

import nl.hu.cisq1.lingo.trainer.application.UnsupportedWordLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DefaultLengthStrategyTest {
    DefaultLengthStrategy defaultLengthStrategy = new DefaultLengthStrategy();

    private static Stream<Arguments> provideCorrectWordLengthExamples() {
        return Stream.of(
                Arguments.of(0, 5),
                Arguments.of(5, 6),
                Arguments.of(6, 7),
                Arguments.of(7, 5)
        );
    }

    private static Stream<Arguments> provideIncorrectWordLengthExamples() {
        return Stream.of(
                Arguments.of(4),
                Arguments.of(8),
                Arguments.of(300),
                Arguments.of(-10)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCorrectWordLengthExamples")
    @DisplayName("gives back the correct length")
    void calculateCorrectWordLength(int previous, int expected) {
        int actual = defaultLengthStrategy.calculateWordLength(previous);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectWordLengthExamples")
    @DisplayName("throws error when length is not supported")
    void calculateIncorrectWordLength(int length) {
        assertThrows(UnsupportedWordLengthException.class, () ->
                defaultLengthStrategy.calculateWordLength(length));
    }
}