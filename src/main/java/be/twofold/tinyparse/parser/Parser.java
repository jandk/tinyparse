package be.twofold.tinyparse.parser;

import be.twofold.tinyparse.*;
import be.twofold.tinyparse.lexer.*;

import java.util.*;
import java.util.function.*;

public interface Parser<T> {
    Result<T> parse(Tokens tokens);

    default <R> Parser<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return Parsers.map(this, mapper);
    }

    default <R> Parser<Pair<T, R>> and(Parser<R> other) {
        Objects.requireNonNull(other, "other");
        return Parsers.and(this, other);
    }

    default Parser<T> or(Parser<T> other) {
        Objects.requireNonNull(other, "other");
        return Parsers.or(this, other);
    }
}
