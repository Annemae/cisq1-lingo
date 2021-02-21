package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(Word.of("BALEN"), "AVOND", List.of('.', '.', '.', '.', '.'), List.of('.', '.', '.', '.', '.')),
                Arguments.of(Word.of("SCHOEN"), "ACTIES", List.of('.', '.', '.', '.', '.', '.'), List.of('.', 'C', '.', '.', 'E', '.')),
                Arguments.of(Word.of("ALARM"), "ATOOM", List.of('A', '.', '.', '.', 'M'), List.of('A', '.', '.', '.', 'M')),
                Arguments.of(Word.of("BRAAD"), "BROOD", List.of('B', '.', '.', '.', 'D'), List.of('B', 'R', '.', '.', 'D')),
                Arguments.of(Word.of("BROOD"), "BROOD", List.of('B', 'R', '.', '.', 'D'), List.of('B', 'R', 'O', 'O', 'D'))
        );
    }

//    private static Stream<Arguments> provideWrongHintExamples() {
//        return Stream.of(
//                Arguments.of(Word.of("WATER"), "BROOD", List.of('.', '.', '.', '.', '.')),
//                Arguments.of(Word.of("SCHOEN"), "ACTIES", List.of('.', '.', '.', '.', '.'))
//        );
//    }


    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct")
    void hintIsCorrect(Word wordToGuess, String attempt, List<Character> previousHint, List<Character> expectedHint) {
        Feedback feedback = Feedback.of(attempt, wordToGuess);

        List<Character> actualHint = Hint.of(previousHint, wordToGuess, feedback.getMarks()).getHint();

        assertEquals(expectedHint, actualHint);
    }

//    @ParameterizedTest
//    @MethodSource("provideWrongHintExamples")
//    @DisplayName("hint is incorrect when wordToGuess length or previousHint length differ from marks length")
//    void hintLengthIsIncorrect(Word wordToGuess, String attempt, List<Character> previousHint) { //Nu nog length maar kan later aangepast worden.
//        Feedback feedback = Feedback.of(attempt, wordToGuess);
//
//        assertThrows(InvalidHintException.class,
//                () -> Hint.of(previousHint, wordToGuess, feedback.getMarks()));
//    }
}