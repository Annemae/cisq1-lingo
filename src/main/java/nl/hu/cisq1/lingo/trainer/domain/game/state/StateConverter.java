package nl.hu.cisq1.lingo.trainer.domain.game.state;

import javax.persistence.AttributeConverter;

public class StateConverter implements AttributeConverter<State, String> {
    @Override
    public String convertToDatabaseColumn(State state) {
        return state.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public State convertToEntityAttribute(String string) {
        if ("activestate".equals(string)) {
            return new ActiveState();
        } else return new InactiveState();
    }
}