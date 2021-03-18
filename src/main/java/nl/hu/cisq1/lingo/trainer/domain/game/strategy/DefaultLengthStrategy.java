package nl.hu.cisq1.lingo.trainer.domain.game.strategy;

import nl.hu.cisq1.lingo.trainer.application.UnsupportedWordLengthException;

public class DefaultLengthStrategy implements WordLengthStrategy {

    @Override
    public int calculateWordLength(int previousLength) {
        switch (previousLength) {
            case 0:
            case 7:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            default:
                throw new UnsupportedWordLengthException("Word length is not supported.");
        }
    }
}
