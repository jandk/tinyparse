package be.twofold.tinyparse.exception;

public final class LexerException extends RuntimeException {
    public LexerException(char c) {
        super("Can't tokenize '" + c + "'");
    }
}
