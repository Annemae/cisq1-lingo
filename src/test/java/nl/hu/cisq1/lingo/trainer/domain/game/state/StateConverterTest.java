package nl.hu.cisq1.lingo.trainer.domain.game.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StateConverterTest {
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
    @DisplayName("Convert to database column")
    void convertToDatabaseColumn(State state, String expected) {
        StateConverter stateConverter = new StateConverter();

        String actual = stateConverter.convertToDatabaseColumn(state);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideStateExamples")
    @DisplayName("Convert to entity attribute")
    void convertToEntityAttribute(State expected, String string) {
        StateConverter stateConverter = new StateConverter();

        State actual = stateConverter.convertToEntityAttribute(string);

        assertTrue(actual.getClass().isAssignableFrom(expected.getClass()));
    }
}