package nl.hu.cisq1.lingo.trainer.domain.game.strategy;

public class DefaultLengthStrategy implements WordLengthStrategy {
    @Override
    public int calculateWordLength(int previousLength) {
        switch (previousLength) {
            case 5:
                return 6;
            case 6:
                return 7;
            case 0:
            case 7:
            default:
                return 5;
        }
    }
}
