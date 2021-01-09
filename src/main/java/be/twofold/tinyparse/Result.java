package be.twofold.tinyparse;

import be.twofold.tinyparse.lexer.*;

import java.util.*;

public abstract class Result<T> {

    Result() {
    }

    public static <T> Result<T> success(T value, Tokens left) {
        return new Success<>(value, left);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> failure(String cause) {
        return (Result<T>) new Failure(cause);
    }

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public boolean isFailure() {
        return this instanceof Failure;
    }

    @SuppressWarnings("unchecked")
    public <R> Result<R> cast() {
        if (isFailure()) {
            return (Result<R>) this;
        }
        throw new ClassCastException("Cannot cast a success");
    }

    public abstract T getValue();

    public abstract Tokens getLeft();

    private static final class Success<T> extends Result<T> {
        private final T value;
        private final Tokens left;

        Success(T value, Tokens left) {
            this.value = value;
            this.left = left;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public Tokens getLeft() {
            return left;
        }

        @Override
        public String toString() {
            return "Success(" + value + ")";
        }
    }

    private static final class Failure extends Result<Void> {
        private final String cause;

        Failure(String cause) {
            this.cause = cause;
        }

        @Override
        public Void getValue() {
            throw new NoSuchElementException();
        }

        @Override
        public Tokens getLeft() {
            throw new NoSuchElementException();
        }

        public String getCause() {
            return cause;
        }

        @Override
        public String toString() {
            return "Failure(" + cause + ")";
        }
    }

}
