package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidGuessException extends RuntimeException {
    public InvalidGuessException(String string) {
        super(string);
    }
}
