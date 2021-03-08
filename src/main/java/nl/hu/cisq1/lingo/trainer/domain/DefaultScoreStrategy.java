package nl.hu.cisq1.lingo.trainer.domain;

public class DefaultScoreStrategy implements ScoreStrategy {

    @Override
    public int calculateScore(Round round) {
        int attempts = round.amountOfAttempts();

        return 5 * (5 - attempts) + 5;
    }
}
