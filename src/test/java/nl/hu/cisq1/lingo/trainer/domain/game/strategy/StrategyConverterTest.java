package nl.hu.cisq1.lingo.trainer.domain.game.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StrategyConverterTest {
    private final StrategyConverter strategyConverter = new StrategyConverter();

    private static Stream<Arguments> provideStrategyExamples() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("defaultlengthstrategy"),
                Arguments.of("DEFAULTLENGTHSTRATEGY"),
                Arguments.of("actuallyNotAStrategy"));
    }

    @ParameterizedTest
    @MethodSource("provideStrategyExamples")
    @DisplayName("convert to entity attribute (default): default length strategy")
    void convertToEntityAttribute(String expected) {
        WordLengthStrategy wordLengthStrategy = strategyConverter.convertToEntityAttribute(expected);

        assertTrue(wordLengthStrategy.getClass().isAssignableFrom(DefaultLengthStrategy.class));
    }

    @Test
    @DisplayName("convert to database column")
    void convertToDatabaseColumn() {
        String actual = strategyConverter.convertToDatabaseColumn(new DefaultLengthStrategy());

        assertEquals("defaultlengthstrategy", actual);
    }
}