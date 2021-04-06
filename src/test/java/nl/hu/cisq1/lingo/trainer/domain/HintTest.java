package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private static final Word BREAD = Word.of("BREAD");

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(Collections.emptyList(),
                        List.of('B', '.', '.', '.', '.')), //Testing if initial hint is correct by not giving any feedback.

                Arguments.of(List.of(Feedback.of("BINGO", BREAD)),
                        List.of('B', '.', '.', '.', '.')), //Testing that it doesn't change after wrong 1st feedback.

                Arguments.of(List.of(Feedback.of("BRAND", BREAD)),
                        List.of('B', 'R', '.', '.', 'D')), //Testing that it does change after good 1st feedback.

                Arguments.of(List.of(Feedback.of("BREAD", BREAD)),
                        List.of('B', 'R', 'E', 'A', 'D')), //Testing that it gives back full hint after right 1st feedback.

                Arguments.of(List.of(Feedback.of("BINGO", BREAD), Feedback.of("BRAND", BREAD)),
                        List.of('B', 'R', '.', '.', 'D')), //Testing that it gives back most recent hint after two feedbacks.

                Arguments.of(List.of(Feedback.of("BATH", BREAD)),
                        List.of('B', '.', '.', '.', '.')), //Testing it gives back most recent hint when invalid guess is given.

                Arguments.of(List.of(Feedback.of("BROTHER", BREAD)),
                        List.of('B', '.', '.', '.', '.')), //Testing it gives back most recent hint when invalid guess is given.

                Arguments.of(List.of(Feedback.of("COCOA", BREAD)),
                        List.of('B', '.', '.', '.', '.')), //Testing it gives back most recent hint when invalid guess is given.

                Arguments.of(List.of(Feedback.of("BINGO", BREAD), Feedback.of("COCOA", BREAD)),
                        List.of('B', '.', '.', '.', '.')) //Testing that it gives back most recent hint after two feedbacks and last feedback is wrong.
        );
    }

    static Stream<Arguments> provideEqualsExamples() {
        Hint hint = new Hint(Collections.emptyList(), BREAD);
        return Stream.of(
                Arguments.of(hint,
                        hint,
                        true),
                Arguments.of(hint,
                        null,
                        false),
                Arguments.of(hint,
                        new Hint(Collections.emptyList(), Word.of("ACORN")),
                        false
                )
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
    void equalsWorks(Hint hintOne, Hint hintTwo, boolean isEqual) {
        assertEquals(hintOne.equals(hintTwo), isEqual);
    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Hint expected = new Hint(Collections.emptyList(), BREAD);
        Hint actual = Hint.of(Collections.emptyList(), BREAD);

        assertEquals(expected.hashCode(), actual.hashCode());
    }
}
