package nl.hu.cisq1.lingo.trainer.domain.game.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyConverterTest {
    private final StrategyConverter strategyConverter = new StrategyConverter();
//todo fix op default als anders is
    @Test
    @DisplayName("convert to database column")
    void convertToDatabaseColumn() {
        String actual = strategyConverter.convertToDatabaseColumn(new DefaultLengthStrategy());

        assertEquals("defaultlengthstrategy", actual);
    }

    @Test
    @DisplayName("convert to entity attribute")
    void convertToEntityAttribute() {
        WordLengthStrategy actual = strategyConverter.convertToEntityAttribute("defaultlengthstrategy");

        assertTrue(actual.getClass().isAssignableFrom(DefaultLengthStrategy.class));
    }

}