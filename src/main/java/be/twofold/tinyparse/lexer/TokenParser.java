package be.twofold.tinyparse.lexer;

import be.twofold.tinyparse.*;
import be.twofold.tinyparse.exception.*;
import be.twofold.tinyparse.parser.*;

import java.util.*;

final class TokenParser implements Parser<Token> {
    private TokenType type;

    TokenParser(TokenType type) {
        this.type = Objects.requireNonNull(type, "type");
    }

    @Override
    public Result<Token> parse(Tokens tokens) {
        Token current = tokens.current();
        if (current == null) {
            throw new ParserException("Unexpected EOF");
        }
        if (current.getType().equals(type)) {
            return Result.success(current, tokens.drop(1));
        }
        if (current.getType().isIgnored()) {
            return parse(tokens.drop(1));
        }
        throw new ParserException("Unexpected token: " + current.getType());
    }
}
