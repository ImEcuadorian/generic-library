package io.github.imecuadorian.library;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Files} utility class.
 * <p>
 * This class contains unit tests for verifying the functionality of the Files class,
 * which provides methods for file and directory operations such as creating, reading,
 * writing, filtering, and selecting files or directories.
 * <p>
 * The tests use JUnit 5's {@link TempDir} annotation to create temporary directories
 * and files for testing, ensuring that tests don't interfere with the actual file system
 * and are automatically cleaned up after test execution.
 * <p>
 * Each test method focuses on testing a specific method or functionality of the Files class,
 * following the Arrange-Act-Assert pattern to ensure clear and maintainable tests.
 */
class FilesTest {

    @TempDir
    Path tempDir;

    /**
     * Tests the constructor of the Files class.
     * Verifies that a Files object is correctly initialized with a given path.
     */
    @Test
    void testConstructor() {
        // Arrange
        String pathName = tempDir.toString();

        // Act
        Files files = new Files(pathName);

        // Assert
        assertNotNull(files, "Files object should not be null");
        assertEquals(tempDir.toFile(), files.getFile(), "File object should match the provided path");
    }

    /**
     * Tests the createFile method with FILE type.
     * Verifies that a file is correctly created when it doesn't exist.
     */
    @Test
    void testCreateFile() throws IOException {
        // Arrange
        Path filePath = tempDir.resolve("testFile.txt");
        Files files = new Files(filePath.toString());

        // Act
        boolean result = files.createFile(FileType.FILE);

        // Assert
        assertTrue(result, "File creation should return true");
        assertTrue(java.nio.file.Files.exists(filePath), "File should exist after creation");
    }

    /**
     * Tests the createFile method with DIRECTORY type.
     * Verifies that a directory is correctly created when it doesn't exist.
     */
    @Test
    void testCreateDirectory() throws IOException {
        // Arrange
        Path dirPath = tempDir.resolve("testDir");
        Files files = new Files(dirPath.toString());

        // Act
        boolean result = files.createFile(FileType.DIRECTORY);

        // Assert
        assertTrue(result, "Directory creation should return true");
        assertTrue(java.nio.file.Files.exists(dirPath), "Directory should exist after creation");
        assertTrue(java.nio.file.Files.isDirectory(dirPath), "Created path should be a directory");
    }

    /**
     * Tests the createFile method when the file already exists.
     * Verifies that the method returns false when attempting to create an existing file.
     */
    @Test
    void testCreateFileWhenExists() throws IOException {
        // Arrange
        Path filePath = tempDir.resolve("existingFile.txt");
        java.nio.file.Files.createFile(filePath);
        Files files = new Files(filePath.toString());

        // Act
        boolean result = files.createFile(FileType.FILE);

        // Assert
        assertFalse(result, "Creating an existing file should return false");
    }

    /**
     * Tests the writeFile method in append mode.
     * Verifies that content is correctly appended to an existing file.
     */
    @Test
    void testWriteFileAppend() throws IOException {
        // Arrange
        Path filePath = tempDir.resolve("appendTest.txt");
        Files files = new Files(filePath.toString());
        files.createFile(FileType.FILE);
        
        // Act
        files.writeFile("First line", false);
        files.writeFile("Second line", false);
        
        // Assert
        String content = new String(java.nio.file.Files.readAllBytes(filePath));
        assertTrue(content.contains("First line"), "File should contain the first line");
        assertTrue(content.contains("Second line"), "File should contain the second line");
    }

    /**
     * Tests the writeFile method in overwrite mode.
     * Verifies that content correctly overwrites an existing file.
     */
    @Test
    void testWriteFileOverwrite() throws IOException {
        // Arrange
        Path filePath = tempDir.resolve("overwriteTest.txt");
        Files files = new Files(filePath.toString());
        files.createFile(FileType.FILE);
        
        // Act
        files.writeFile("Original content", false);
        files.writeFile("New content", true);
        
        // Assert
        String content = new String(java.nio.file.Files.readAllBytes(filePath));
        assertFalse(content.contains("Original content"), "File should not contain the original content");
        assertTrue(content.contains("New content"), "File should contain the new content");
    }

    /**
     * Tests the readFile method.
     * Verifies that the content of a file is correctly read.
     */
    @Test
    void testReadFile() throws IOException {
        // Arrange
        Path filePath = tempDir.resolve("readTest.txt");
        String expectedContent = "Test content" + System.lineSeparator();
        java.nio.file.Files.writeString(filePath, expectedContent);
        Files files = new Files(filePath.toString());
        
        // Act
        String content = files.readFile();
        
        // Assert
        assertEquals(expectedContent, content, "Read content should match the written content");
    }

    /**
     * Tests the validateByRegularExpression method.
     * Verifies that the method correctly validates strings against regex patterns.
     */
    @Test
    void testValidateByRegularExpression() {
        // Arrange & Act & Assert
        assertTrue(Files.validateByRegularExpression("abc123", "\\w+"), "Alphanumeric string should match \\w+");
        assertTrue(Files.validateByRegularExpression("123", "\\d+"), "Numeric string should match \\d+");
        assertFalse(Files.validateByRegularExpression("abc", "\\d+"), "Alphabetic string should not match \\d+");
    }

    /**
     * Tests the replaceByRegularExpression method.
     * Verifies that the method correctly replaces text based on regex patterns.
     */
    @Test
    void testReplaceByRegularExpression() {
        // Arrange
        String text = "Hello 123 World";
        
        // Act
        String result1 = Files.replaceByRegularExpression(text, "\\d+", "numbers");
        String result2 = Files.replaceByRegularExpression(text, "\\s+", "-");
        
        // Assert
        assertEquals("Hello numbers World", result1, "Numbers should be replaced");
        assertEquals("Hello-123-World", result2, "Spaces should be replaced with hyphens");
    }

    /**
     * Tests the findWords method.
     * Verifies that the method correctly finds words matching a regex pattern.
     */
    @Test
    void testFindWords() {
        // Arrange
        Files files = new Files(tempDir.toString());
        String text = "Hello world, this is a test. It has 123 numbers and special-chars.";
        
        // Act
        List<String> words = files.findWords(text, "\\w+");
        List<String> numbers = files.findWords(text, "\\d+");
        
        // Assert
        assertTrue(words.contains("Hello"), "Should find the word 'Hello'");
        assertTrue(words.contains("world"), "Should find the word 'world'");
        assertTrue(words.contains("123"), "Should find the number '123'");
        assertEquals(1, numbers.size(), "Should find exactly one number");
        assertEquals("123", numbers.get(0), "Should find the number '123'");
    }

    /**
     * Tests the listFiles method.
     * Verifies that the method correctly lists files in a directory.
     */
    @Test
    void testListFiles() throws IOException {
        // Arrange
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        java.nio.file.Files.createFile(file1);
        java.nio.file.Files.createFile(file2);
        Files files = new Files(tempDir.toString());
        
        // Act
        String[] fileList = files.listFiles();
        
        // Assert
        assertEquals(2, fileList.length, "Should list 2 files");
        List<String> fileNames = Arrays.asList(fileList);
        assertTrue(fileNames.contains("file1.txt"), "Should contain file1.txt");
        assertTrue(fileNames.contains("file2.txt"), "Should contain file2.txt");
    }

    /**
     * Tests the listFilesOnDirectory method with FILE type.
     * Verifies that the method correctly lists only files (not directories).
     */
    @Test
    void testListFilesOnDirectoryWithFileType() throws IOException {
        // Arrange
        Path file = tempDir.resolve("testFile.txt");
        Path dir = tempDir.resolve("testDir");
        java.nio.file.Files.createFile(file);
        java.nio.file.Files.createDirectory(dir);
        Files files = new Files(tempDir.toString());
        
        // Act
        List<String> fileList = files.listFilesOnDirectory(FileType.FILE);
        
        // Assert
        assertEquals(1, fileList.size(), "Should list 1 file");
        assertEquals("testFile.txt", fileList.get(0), "Should list the file name");
    }

    /**
     * Tests the listFilesOnDirectory method with DIRECTORY type.
     * Verifies that the method correctly lists only directories (not files).
     */
    @Test
    void testListFilesOnDirectoryWithDirectoryType() throws IOException {
        // Arrange
        Path file = tempDir.resolve("testFile.txt");
        Path dir = tempDir.resolve("testDir");
        java.nio.file.Files.createFile(file);
        java.nio.file.Files.createDirectory(dir);
        Files files = new Files(tempDir.toString());
        
        // Act
        List<String> dirList = files.listFilesOnDirectory(FileType.DIRECTORY);
        
        // Assert
        assertEquals(1, dirList.size(), "Should list 1 directory");
        assertEquals("testDir", dirList.get(0), "Should list the directory name");
    }

    /**
     * Tests the setFile and getFile methods.
     * Verifies that the file reference can be correctly updated and retrieved.
     */
    @Test
    void testSetAndGetFile() {
        // Arrange
        Files files = new Files(tempDir.toString());
        File newFile = new File(tempDir.toString(), "newFile.txt");
        
        // Act
        files.setFile(newFile);
        File retrievedFile = files.getFile();
        
        // Assert
        assertEquals(newFile, retrievedFile, "Retrieved file should match the set file");
    }

    /**
     * Tests the getFileFromFileChooser method.
     * This test is limited as it involves UI interaction, but we can test the method signature.
     * In a real environment, this would require UI automation or mocking.
     */
    @Test
    @Disabled("This test requires UI interaction and is for demonstration only")
    void testGetFileFromFileChooser() {
        // This test is disabled as it requires UI interaction
        // In a real test environment, you would use UI automation or mock the JFileChooser
        Files files = new Files(tempDir.toString());
        JFrame frame = new JFrame();
        boolean result = files.getFileFromFileChooser(frame, "txt");
        // No assertions as this is just a demonstration
    }
}