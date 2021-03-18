package nl.hu.cisq1.lingo.trainer.domain.game.strategy;


import javax.persistence.AttributeConverter;

public class StrategyConverter implements AttributeConverter<WordLengthStrategy, String> {
    @Override
    public String convertToDatabaseColumn(WordLengthStrategy wordLengthStrategy) {
        return wordLengthStrategy.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public WordLengthStrategy convertToEntityAttribute(String string) {
        return new DefaultLengthStrategy();
    }
}
