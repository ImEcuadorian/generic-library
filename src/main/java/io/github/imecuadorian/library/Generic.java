package io.github.imecuadorian.library;

import lombok.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static java.util.Arrays.*;
import static java.util.Comparator.*;

/**
 * A generic class that can hold two elements of a generic type T and two elements of a generic type S.
 * It also supports operations on these generic types, such as validation and computation.
 *
 * @param <T> the type of the first set of elements
 * @param <S> the type of the second set of elements
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Generic<T, S> {

    private T t1;
    private T t2;
    private S s1;
    private S s2;
    private T[] array;


    /**
     * Constructs a new {@code Generic} instance with the specified first element of type {@code T}.
     *
     * @param t1 the first element of type {@code T}; can be {@code null}
     */
    public Generic(T t1) {
        this(t1, null);
    }

    /**
     * Constructs a new {@code Generic} instance with the specified first and second elements of type {@code T}
     * and the specified first element of type {@code S}.
     *
     * @param t1 the first element of type {@code T}; can be {@code null}
     * @param s1 the first element of type {@code S}; can be {@code null}
     */
    public Generic(T t1, S s1) {
        this(t1, null, s1);
    }

    /**
     * Constructs a new {@code Generic} instance with the specified first and second elements of type {@code T}
     * and the specified first element of type {@code S}.
     *
     * @param t1 the first element of type {@code T}; can be {@code null}
     * @param t2 the second element of type {@code T}; can be {@code null}
     * @param s1 the first element of type {@code S}; can be {@code null}
     */
    public Generic(T t1, T t2, S s1) {
        this(t1, t2, s1, null);
    }

    /**
     * Constructs a new {@code Generic} instance with the specified first and second elements of type {@code T}
     * and the specified first and second elements of type {@code S}.
     *
     * @param t1 the first element of type {@code T}; can be {@code null}
     * @param t2 the second element of type {@code T}; can be {@code null}
     * @param s1 the first element of type {@code S}; can be {@code null}
     * @param s2 the second element of type {@code S}; can be {@code null}
     */
    public Generic(T t1, T t2, S s1, S s2) {
        this.t1 = t1;
        this.t2 = t2;
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * Validates whether the string representations of the first and second elements of the given {@code Generic} object are equal.
     *
     * @param values a {@code Generic} instance containing elements to be compared; must not be {@code null}
     * @return {@code true} if the string representation of {@code values.getT1()} equals the string representation of {@code values.getT2()}, {@code false} otherwise
     */
    public boolean validateWord(@NotNull Generic<?, ?> values) {
        return values.getT1().toString().equals(values.getT2().toString());
    }

    /**
     * Validates whether the numeric values of the first and second elements in the given {@code Generic} object are equal.
     *
     * @param values a {@code Generic} instance containing two numeric elements to be compared; must not be {@code null}
     * @return {@code true} if the numeric value of {@code values.getT1()} is equal to the numeric value of {@code values.getT2()}, {@code false} otherwise
     */
    public boolean validateNumber(@NotNull Generic<? extends Number, ?> values) {
        return values.getT1().doubleValue() == values.getT2().doubleValue();
    }

    /**
     * Computes the maximum integer value from the array of numeric elements contained within the provided
     * {@code Generic} instance. The array is retrieved using the {@code getArray()} method of the {@code Generic} instance.
     *
     * @param values a {@code Generic} instance containing an array of numeric elements; must not be {@code null}.
     * @return the maximum integer value from the numeric elements in the array.
     * @throws NullPointerException if the array in the {@code Generic} instance is {@code null} or contains only {@code null} values.
     */
    public int numberMax(@NotNull Generic<? extends Number, ?> values) {
        if (values.getArray() == null) {
            throw new NullPointerException("Array is null");
        }
        return stream(values.getArray()).max(comparingDouble(Number::doubleValue)).
                orElseThrow(() -> new NullPointerException("Array is empty")).intValue();
    }

    /**
     * Converts a delimited string into an array of double values.
     * Each value in the string is separated by the specified delimiter
     * and is parsed into a double. Whitespace around each value is trimmed.
     *
     * @param words the string containing the numeric values separated by the delimiter; must not be null
     * @param delimiter the delimiter used to separate numeric values within the string; must not be null
     * @return an array of double values parsed from the input string
     * @throws NumberFormatException if any value in the string is not a valid double
     * @throws IllegalArgumentException if the resulting array of double values is empty
     */
    public double[] getValuesFromWords(@NotNull T words, @NotNull T delimiter) {
        String[] stringArray = words.toString().split(delimiter.toString());
        double[] result = Arrays.stream(stringArray)
                .map(String::trim)
                .mapToDouble(s -> {
                    try {
                        return Double.parseDouble(s);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Invalid number format: " + s);
                    }
                })
                .toArray();

        if (result.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        return result;
    }

}
