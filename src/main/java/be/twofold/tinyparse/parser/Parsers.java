package be.twofold.tinyparse.parser;

import be.twofold.tinyparse.*;
import be.twofold.tinyparse.lexer.*;

import java.util.function.*;

final class Parsers {
    private Parsers() {
    }

    static <T, U> Parser<Pair<T, U>> and(Parser<T> parser1, Parser<U> parser2) {
        return new AndParser<>(parser1, parser2);
    }

    static <T> Parser<T> or(Parser<T> parser1, Parser<T> parser2) {
        return new OrParser<>(parser1, parser2);
    }

    static final class AndParser<T, U> implements Parser<Pair<T, U>> {
        private final Parser<T> parser1;
        private final Parser<U> parser2;

        AndParser(Parser<T> parser1, Parser<U> parser2) {
            this.parser1 = parser1;
            this.parser2 = parser2;
        }

        @Override
        public Result<Pair<T, U>> parse(Tokens tokens) {
            Result<T> result1 = parser1.parse(tokens);
            if (result1.isFailure()) return result1.cast();

            Result<U> result2 = parser2.parse(result1.getLeft());
            if (result2.isFailure()) return result2.cast();

            Pair<T, U> value = new Pair<>(result1.getValue(), result2.getValue());
            return Result.success(value, result2.getLeft());
        }
    }

    static final class OrParser<T> implements Parser<T> {
        private final Parser<T> parser1;
        private final Parser<T> parser2;

        OrParser(Parser<T> parser1, Parser<T> parser2) {
            this.parser1 = parser1;
            this.parser2 = parser2;
        }

        @Override
        public Result<T> parse(Tokens tokens) {
            Result<T> result1 = parser1.parse(tokens);
            if (result1.isSuccess()) return result1;

            Result<T> result2 = parser2.parse(tokens);
            if (result2.isSuccess()) return result2;

            return Result.failure("Both alternatives failed");
        }
    }


    static <T, R> Parser<R> map(Parser<T> parser, Function<? super T, ? extends R> mapper) {
        return new MapParser<>(parser, mapper);
    }

    static final class MapParser<T, R> implements Parser<R> {
        private final Parser<T> parser;
        private final Function<? super T, ? extends R> mapper;

        MapParser(Parser<T> parser, Function<? super T, ? extends R> mapper) {
            this.parser = parser;
            this.mapper = mapper;
        }

        @Override
        public Result<R> parse(Tokens tokens) {
            Result<T> result = parser.parse(tokens);
            if (result.isFailure()) return result.cast();

            return Result.success(mapper.apply(result.getValue()), result.getLeft());
        }
    }

}
