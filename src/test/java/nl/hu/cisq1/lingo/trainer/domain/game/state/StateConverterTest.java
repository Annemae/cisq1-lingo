package nl.hu.cisq1.lingo.trainer.domain.game.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StateConverterTest {
    private final StateConverter stateConverter = new StateConverter();

    private static Stream<Arguments> provideStateExamples() {
        return Stream.of(
                Arguments.of(new ActiveState(),
                        "activestate"),
                Arguments.of(new InactiveState(),
                        "inactivestate")
        );
    }

    @ParameterizedTest
    @MethodSource("provideStateExamples")
    @DisplayName("convert to database column")
    void convertToDatabaseColumn(State state, String expected) {
        String actual = stateConverter.convertToDatabaseColumn(state);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideStateExamples")
    @DisplayName("convert to entity attribute")
    void convertToEntityAttribute(State expected, String string) {
        State actual = stateConverter.convertToEntityAttribute(string);

        assertTrue(actual.getClass().isAssignableFrom(expected.getClass()));
    }
}