package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

public class HintTest { //todo calculatefirsthint test
    private static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(new Hint(List.of('.', '.', '.', '.', '.')),
                        Word.of("BALEN"),
                        List.of(PRESENT, ABSENT, ABSENT, PRESENT, ABSENT),
                        new Hint(List.of('.', '.', '.', '.', '.'))),
                Arguments.of(new Hint(List.of('.', '.', '.', '.', '.', '.')),
                        Word.of("SCHOEN"),
                        List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT, PRESENT),
                        new Hint(List.of('.', 'C', '.', '.', 'E', '.'))),
                Arguments.of(new Hint(List.of('A', '.', '.', '.', 'M')),
                        Word.of("ALARM"),
                        List.of(CORRECT, ABSENT, ABSENT, ABSENT, CORRECT),
                        new Hint(List.of('A', '.', '.', '.', 'M'))),
                Arguments.of(new Hint(List.of('B', '.', '.', '.', 'D')),
                        Word.of("BRAAD"),
                        List.of(CORRECT, CORRECT, ABSENT, ABSENT, CORRECT),
                        new Hint(List.of('B', 'R', '.', '.', 'D'))),
                Arguments.of(new Hint(List.of('B', 'R', '.', '.', 'D')),
                        Word.of("BROOD"),
                        List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT),
                        new Hint(List.of('B', 'R', 'O', 'O', 'D')))
        );
    }

    private static Stream<Arguments> provideWrongHintExamples() {
        return Stream.of(
                Arguments.of(new Hint(List.of('.', '.', '.', '.', '.')),
                        Word.of("WATER"),
                        List.of(INVALID, INVALID, INVALID, INVALID)),
                Arguments.of(new Hint(List.of('.', '.', '.', '.', '.')),
                        Word.of("WATER"),
                        List.of(INVALID, INVALID, INVALID, INVALID, INVALID, INVALID))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct")
    void hintIsCorrect(Hint previousHint, Word wordToGuess, List<Mark> marks, Hint expectedHint) {
        Hint hint = Hint.of(previousHint, wordToGuess, marks);

        assertEquals(expectedHint.getHint(), hint.getHint());
    }

    @ParameterizedTest
    @MethodSource("provideWrongHintExamples")
    @DisplayName("hint is incorrect when wordToGuess length or previousHint length differ from marks length")
    void hintLengthIsIncorrect(Hint previousHint, Word wordToGuess, List<Mark> marks) { //Nu nog length maar kan later aangepast worden.

        assertThrows(InvalidHintException.class,
                () -> Hint.of(previousHint, wordToGuess, marks));
    }

    @Test
    @DisplayName("static constructor gives the same object back as new")
    void staticConstructorWorks() {
        Hint expected = new Hint(List.of('B', 'R', 'A', '.', 'D'));
        Hint actual = Hint.of(new Hint(List.of('B', 'R', '.', '.', 'D')), Word.of("BRAAD"), List.of(CORRECT, CORRECT, CORRECT, ABSENT, CORRECT));

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}
