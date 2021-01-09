package be.twofold.tinyparse.lexer;

import java.util.*;

public final class Token {

    private final TokenType type;
    private final String match;

    public Token(TokenType type, String match) {
        this.type = Objects.requireNonNull(type, "type");
        this.match = Objects.requireNonNull(match, "match");
    }

    public TokenType getType() {
        return type;
    }

    public String getMatch() {
        return match;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Token)) return false;

        Token that = (Token) obj;
        return Objects.equals(type, that.type)
            && Objects.equals(match, that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, match);
    }

    @Override
    public String toString() {
        return "Token(type=" + type.getName() + ", match='" + match + "')";
    }

}
