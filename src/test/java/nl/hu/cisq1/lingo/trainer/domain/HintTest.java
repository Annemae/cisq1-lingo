package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private static final Word BREAD = Word.of("BREAD");
    private static final List<Feedback> FEEDBACK = new ArrayList<>();

    private static Stream<Arguments> provideHintExamplesWithValidMarks() {
        return Stream.of(
            Arguments.of(Feedback.of("BINGO", BREAD),
                    List.of('B', 'R', '.', '.', '.')),
            Arguments.of(Feedback.of("BRAND", BREAD),
                    List.of('B', 'R', '.', '.', 'D')),
            Arguments.of(Feedback.of("BREAD", BREAD),
                    List.of('B', 'R', 'E', 'A', 'D'))
        );
    }

    private static Stream<Arguments> provideHintExamplesWithInvalidMarks() {
        return Stream.of(
            Arguments.of(Feedback.of("BATH", BREAD),
                    List.of('B', 'R', '.', '.', '.')),
            Arguments.of(Feedback.of("BROTHER", BREAD),
                    List.of('B', 'R', '.', '.', '.')),
            Arguments.of(Feedback.of("COCOA", BREAD),
                    List.of('B', 'R', '.', '.', '.'))
        );
    }

    @BeforeAll
    static void setUp() { //TODO controleren of ik dit goed gebruik
        FEEDBACK.add(Feedback.of("BROOM", BREAD));
    }

    @ParameterizedTest
    @MethodSource("provideHintExamplesWithValidMarks")
    @DisplayName("hint is correct with given valid marks")
    void hintIsCorrectWithValidMarks(Feedback feedback, List<Character> expected) {
        FEEDBACK.add(feedback); //GIVEN

        Hint hint = Hint.of(FEEDBACK, BREAD); //WHEN

        assertEquals(expected, hint.getHintCharacters()); //THEN
    }

    @ParameterizedTest
    @MethodSource("provideHintExamplesWithInvalidMarks")
    @DisplayName("hint is correct with given invalid marks")
    void hintIsCorrectWithInvalidMarks(Feedback feedback, List<Character> expected) {
        FEEDBACK.add(feedback); //GIVEN

        Hint hint = Hint.of(FEEDBACK, BREAD); //WHEN

        assertEquals(expected, hint.getHintCharacters()); //THEN
    }

    @Test
    @DisplayName("calculating initial/first hint is correct")
    void calculateInitialHintWorks() {
        List<Feedback> emptyFeedbackList = Collections.emptyList(); //GIVEN

        Hint actual = Hint.of(emptyFeedbackList, BREAD); //WHEN
        List<Character> expected = List.of('B', '.', '.', '.', '.');

        assertEquals(expected, actual.getHintCharacters()); //THEN
    }

    @Test
    @DisplayName("static constructor gives the same object back as new keyword")
    void staticConstructorWorks() {
        Hint expected = new Hint(FEEDBACK, BREAD); //WHEN
        Hint actual = Hint.of(FEEDBACK, BREAD);

        assertEquals(expected.hashCode(), actual.hashCode()); //THEN
        assertEquals(expected, actual);
    }
}
