package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private static final Word BREAD = Word.of("BREAD");

    private static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(Collections.emptyList(),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of(Feedback.of("BINGO", BREAD)),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of(Feedback.of("BRAND", BREAD)),
                        List.of('B', 'R', '.', '.', 'D')),
                Arguments.of(List.of(Feedback.of("BREAD", BREAD)),
                        List.of('B', 'R', 'E', 'A', 'D')),


                Arguments.of(List.of(Feedback.of("BINGO", BREAD), Feedback.of("BRAND", BREAD)),
                        List.of('B', 'R', '.', '.', 'D')),
                Arguments.of(List.of(Feedback.of("BINGO", BREAD), Feedback.of("BRAND", BREAD), Feedback.of("BREAD", BREAD)),
                        List.of('B', 'R', 'E', 'A', 'D')),

                Arguments.of(List.of(Feedback.of("BATH", BREAD)),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of(Feedback.of("BROTHER", BREAD)),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of(Feedback.of("COCOA", BREAD)),
                        List.of('B', '.', '.', '.', '.')),
                Arguments.of(List.of(Feedback.of("BINGO", BREAD), Feedback.of("COCOA", BREAD)),
                        List.of('B', '.', '.', '.', '.'))
        );
    }

    private static Stream<Arguments> provideEqualsExamples() {
        Hint hint = new Hint(Collections.emptyList(), BREAD);
        return Stream.of(
                Arguments.of(true,
                        new Hint(Collections.emptyList(), BREAD),
                        new Hint(Collections.emptyList(), BREAD)),
                Arguments.of(true,
                        hint,
                        hint),
                Arguments.of(false,
                        hint,
                        null),
                Arguments.of(false,
                        hint,
                        new Hint(Collections.emptyList(), Word.of("ACORN")))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("the correct hints are given back")
    void hintIsCorrectWithGivenFeedbackList(List<Feedback> feedbackList, List<Character> expected) {
        Hint hint = Hint.of(feedbackList, BREAD);

        assertEquals(expected, hint.getHintCharacters());
    }

    @ParameterizedTest
    @MethodSource("provideEqualsExamples")
    @DisplayName("equals test works")
    void equalsWorks(boolean expectedIsEqual, Hint hintOne, Hint hintTwo) {
        assertEquals(expectedIsEqual, hintOne.equals(hintTwo));
    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Hint expected = new Hint(Collections.emptyList(), BREAD);
        Hint actual = Hint.of(Collections.emptyList(), BREAD);

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}
