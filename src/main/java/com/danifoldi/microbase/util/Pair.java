package com.danifoldi.microbase.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Pair<A, B>(A a, B b) {

    public static<A, B> @NotNull Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    @SafeVarargs
    public static<A> @NotNull Pair<A, A> of(A... a) {
        return new Pair<>(a[0], a[1]);
    }

    public static @NotNull Pair<String, String> from(String value) {
        return from(value, '.');
    }

    public static @NotNull Pair<String, String> from(String value, char separator) {
        int dot = value.indexOf(separator);
        String first = value.substring(0, dot);
        String second = value.substring(dot + 1);
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}