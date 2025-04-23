package io.github.imecuadorian.library;

import lombok.*;
import org.jetbrains.annotations.*;

import java.util.*;

import static java.util.Arrays.*;
import static java.util.Comparator.*;

/**
 * A generic utility class that supports dual-type storage and operations.
 * <p>
 * This class allows storage of two elements of type {@code T}, two elements of type {@code S},
 * an array of type {@code T}, and a list of type {@code T}. It includes methods for
 * adding elements, validating values, computing maximums, and parsing strings into numbers.
 *
 * @param <T> the primary type parameter
 * @param <S> the secondary type parameter
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
    private List<T> list;

    /**
     * Constructs a {@code Generic} instance with a single element of type {@code T}.
     *
     * @param t1 the first {@code T} element, may be {@code null}
     */
    public Generic(T t1) {
        this(t1, null);
    }

    /**
     * Constructs a {@code Generic} instance with one element of type {@code T} and one of type {@code S}.
     *
     * @param t1 the first {@code T} element, may be {@code null}
     * @param s1 the first {@code S} element, may be {@code null}
     */
    public Generic(T t1, S s1) {
        this(t1, null, s1);
    }

    /**
     * Constructs a {@code Generic} instance with two elements of type {@code T} and one of type {@code S}.
     *
     * @param t1 the first {@code T} element, may be {@code null}
     * @param t2 the second {@code T} element, may be {@code null}
     * @param s1 the first {@code S} element, may be {@code null}
     */
    public Generic(T t1, T t2, S s1) {
        this(t1, t2, s1, null);
    }

    /**
     * Constructs a fully populated {@code Generic} instance with two {@code T} elements and two {@code S} elements.
     *
     * @param t1 the first {@code T} element
     * @param t2 the second {@code T} element
     * @param s1 the first {@code S} element
     * @param s2 the second {@code S} element
     */
    public Generic(T t1, T t2, S s1, S s2) {
        this.t1 = t1;
        this.t2 = t2;
        this.s1 = s1;
        this.s2 = s2;
        this.list = new ArrayList<>();
    }

    /**
     * Adds an element of type {@code T} to the internal list.
     *
     * @param element the element to add
     */
    public void addElement(T element) {
        list.add(element);
    }

    /**
     * Validates whether the string representations of {@code t1} and {@code t2} in the given object are equal.
     *
     * @param values a {@code Generic} instance with elements to compare
     * @return {@code true} if {@code t1.toString().equals(t2.toString())}, {@code false} otherwise
     */
    public boolean validateWord(@NotNull Generic<?, ?> values) {
        return values.getT1().toString().equals(values.getT2().toString());
    }

    /**
     * Validates whether two {@code Number} elements have equal values.
     *
     * @param values a {@code Generic} instance with numeric elements
     * @return {@code true} if {@code t1.doubleValue() == t2.doubleValue()}, {@code false} otherwise
     */
    public boolean validateNumber(@NotNull Generic<? extends Number, ?> values) {
        return values.getT1().doubleValue() == values.getT2().doubleValue();
    }

    /**
     * Finds the maximum numeric value in the {@code array} field of the provided {@code Generic} instance.
     *
     * @param values a {@code Generic} instance with a numeric array
     * @return the maximum {@code int} value from the array
     * @throws NullPointerException if the array is {@code null} or empty
     */
    public int numberMax(@NotNull Generic<? extends Number, ?> values) {
        if (values.getArray() == null) {
            throw new NullPointerException("Array is null");
        }
        return stream(values.getArray())
                .max(comparingDouble(Number::doubleValue))
                .orElseThrow(() -> new NullPointerException("Array is empty"))
                .intValue();
    }

    /**
     * Converts a delimited string into an array of {@code double} values.
     * <p>
     * Trims and parses each value, throwing an exception if any element is invalid.
     *
     * @param words     the delimited string representing numbers
     * @param delimiter the delimiter to split the string
     * @return an array of {@code double} values
     * @throws NumberFormatException    if any value is not a valid number
     * @throws IllegalArgumentException if the resulting array is empty
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
