package nl.hu.cisq1.lingo.trainer.domain.game.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DefaultLengthStrategyTest {
    DefaultLengthStrategy defaultLengthStrategy = new DefaultLengthStrategy();

    private static Stream<Arguments> provideWordLengthExamples() {
        return Stream.of(
                Arguments.of(0, 5),
                Arguments.of(5, 6),
                Arguments.of(6, 7),
                Arguments.of(7, 5),
                Arguments.of(4, 5),
                Arguments.of(8, 5),
                Arguments.of(300, 5),
                Arguments.of(-10, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWordLengthExamples")
    @DisplayName("the correct lengths are given back")
    void calculateWordLength(int previous, int expected) {
        int actual = defaultLengthStrategy.calculateWordLength(previous);

        assertEquals(expected, actual);
    }
}