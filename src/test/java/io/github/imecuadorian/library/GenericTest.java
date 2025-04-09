package io.github.imecuadorian.library;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GenericTest {

    /**
     * Test class for the Generic class that focuses on verifying the functionality of
     * the method getValuesFromWords, which converts a delimited String into a double array.
     */

    @Test
    void testGetValuesFromWordsWithValidInput() {
        // Arrange
        Generic<String, String> generic = new Generic<>();
        String words = "123.45, 67.89, 10.0";
        String delimiter = ",";

        // Act
        double[] result = generic.getValuesFromWords(words, delimiter);

        // Assert
        assertArrayEquals(new double[]{123.45, 67.89, 10.0}, result, "The resulting array should match the expected values.");
    }

    @Test
    void testGetValuesFromWordsWithWhitespaceHandling() {
        // Arrange
        Generic<String, String> generic = new Generic<>();
        String words = " 15.5 |  20.0 |30.25 ";
        String delimiter = "\\|";

        // Act
        double[] result = generic.getValuesFromWords(words, delimiter);

        // Assert
        assertArrayEquals(new double[]{15.5, 20.0, 30.25}, result, "The resulting array should correctly parse and trim input values.");
    }

    @Test
    void testGetValuesFromWordsWithInvalidNumberFormat() {
        // Arrange
        Generic<String, String> generic = new Generic<>();
        String words = "123.45, abc, 67.89";
        String delimiter = ",";

        // Act & Assert
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            generic.getValuesFromWords(words, delimiter);
        });
        assertEquals("Invalid number format: abc", exception.getMessage(), "The method should throw a NumberFormatException for invalid input.");
    }

    @Test
    void testGetValuesFromWordsWithEmptyInput() {
        // Arrange
        Generic<String, String> generic = new Generic<>();
        String words = "";
        String delimiter = ",";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            generic.getValuesFromWords(words, delimiter);
        });
        assertEquals("Array is empty", exception.getMessage(), "The method should throw an IllegalArgumentException for empty input.");
    }

    @Test
    void testGetValuesFromWordsWithEmptyResultAfterSplitting() {
        // Arrange
        Generic<String, String> generic = new Generic<>();
        String words = ",";
        String delimiter = ",";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            generic.getValuesFromWords(words, delimiter);
        });
        assertEquals("Array is empty", exception.getMessage(), "Empty array after splitting should trigger an IllegalArgumentException.");
    }

    @Test
    void testGetValuesFromWordsWithNonStandardDelimiter() {
        // Arrange
        Generic<String, String> generic = new Generic<>();
        String words = "10#20#30.5#40";
        String delimiter = "#";

        // Act
        double[] result = generic.getValuesFromWords(words, delimiter);

        // Assert
        assertArrayEquals(new double[]{10.0, 20.0, 30.5, 40.0}, result, "The method should correctly parse values with a custom delimiter.");
    }
}