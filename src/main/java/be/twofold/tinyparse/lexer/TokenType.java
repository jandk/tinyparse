package be.twofold.tinyparse.lexer;

import be.twofold.tinyparse.parser.*;

import java.util.*;
import java.util.regex.*;

public final class TokenType {
    private final String name;
    private final Pattern pattern;
    private final boolean ignored;

    private TokenType(String name, String pattern, boolean ignored) {
        this.name = Objects.requireNonNull(name, "name");
        this.pattern = Pattern.compile(pattern);
        this.ignored = ignored;
    }

    public static TokenType tokenize(String name, String pattern) {
        return new TokenType(name, pattern, false);
    }

    public static TokenType ignore(String name, String pattern, boolean ignored) {
        return new TokenType(name, pattern, ignored);
    }

    public String getName() {
        return name;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public Parser<Token> parser() {
        return new TokenParser(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof TokenType
            && Objects.equals(name, ((TokenType) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "TokenType(name='" + name + "', pattern='" + pattern + "', ignored=" + ignored + ")";
    }
}
