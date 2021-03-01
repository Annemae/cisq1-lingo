package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidGameStateException extends RuntimeException {
    public InvalidGameStateException(String string) {
        super(string);
    }
}
