package be.twofold.tinyparse.lexer;

import java.util.*;

public final class Tokens {
    private final List<Token> tokens;
    private final int offset;

    public Tokens(List<Token> tokens) {
        this(tokens, 0);
    }

    private Tokens(List<Token> tokens, int offset) {
        this.tokens = Objects.requireNonNull(tokens, "tokens");
        this.offset = offset;
    }

    Token current() {
        return offset < tokens.size()
            ? tokens.get(offset)
            : null;
    }

    Tokens drop(int count) {
        return new Tokens(tokens, offset + count);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tokens)) return false;

        Tokens that = (Tokens) obj;
        return offset == that.offset
            && Objects.equals(tokens, that.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokens, offset);
    }

    @Override
    public String toString() {
        return "Tokens(" + tokens.subList(offset, tokens.size()) + ")";
    }
}
