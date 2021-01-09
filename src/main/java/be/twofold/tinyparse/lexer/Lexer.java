package be.twofold.tinyparse.lexer;

import be.twofold.tinyparse.exception.*;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public final class Lexer {

    private final List<TokenType> tokenTypes;
    private final List<Pattern> patterns;

    Lexer(List<TokenType> tokenTypes) {
        if (Objects.requireNonNull(tokenTypes, "tokenTypes").isEmpty()) {
            throw new IllegalArgumentException("Need at least one TokenType");
        }

        this.tokenTypes = tokenTypes;
        this.patterns = this.tokenTypes.stream()
            .map(TokenType::getPattern)
            .collect(Collectors.toList());
    }

    public Tokens tokenize(CharSequence input) {
        Matcher matcher = Pattern.compile("").matcher(input);

        List<Token> result = new ArrayList<>();
        while (matcher.regionStart() != input.length()) {
            int index = findPattern(matcher);
            if (index < 0) {
                throw new LexerException(input.charAt(matcher.regionStart()));
            }

            TokenType type = tokenTypes.get(index);
            if (!type.isIgnored()) {
                String match = input.subSequence(matcher.start(), matcher.end()).toString();
                result.add(new Token(type, match));
            }
            matcher.region(matcher.end(), matcher.regionEnd());
        }
        return new Tokens(result);
    }

    private int findPattern(Matcher matcher) {
        for (int i = 0; i < patterns.size(); i++) {
            matcher.usePattern(patterns.get(i));
            if (matcher.lookingAt()) {
                return i;
            }
        }
        return -1;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<TokenType> tokenTypes = new ArrayList<>();
        private final Set<String> tokenTypeNames = new HashSet<>();

        Builder() {
        }

        public Builder add(TokenType type) {
            String typeName = type.getName();
            if (tokenTypeNames.contains(typeName)) {
                throw new IllegalArgumentException("TokenType with name '" + typeName + "' already exists");
            }
            if (type.isIgnored()) {
                // We put the ignored ones in front,
                // because these will be matched the most
                tokenTypes.add(0, type);
            } else {
                tokenTypes.add(type);
            }
            tokenTypeNames.add(typeName);
            return this;
        }

        public Lexer build() {
            return new Lexer(tokenTypes);
        }
    }
}
