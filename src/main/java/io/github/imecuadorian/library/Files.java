package io.github.imecuadorian.library;

import org.jetbrains.annotations.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.*;

/**
 * Utility class for managing file and directory operations.
 * <p>
 * This class provides methods for creating, reading, writing, filtering, and selecting files or directories.
 * It uses a {@code Generic<String, File>} structure to manage associated data.
 */
public class Files {

    private final Generic<String, File> information;

    /**
     * Constructs a {@code Files} instance for managing a specific file or directory path.
     *
     * @param pathName the path to the file or directory
     */
    public Files(String pathName) {
        this.information = new Generic<>(pathName, new File(pathName));
    }

    /**
     * Writes text to the file.
     *
     * @param text  the content to write
     * @param allow if {@code true}, the content will overwrite; otherwise it will append
     * @throws IOException if an I/O error occurs
     */
    public void writeFile(String text, boolean allow) throws IOException {
        try (FileWriter writer = new FileWriter(information.getS1(), !allow)) {
            writer.write(text + System.lineSeparator());
        }
    }

    /**
     * Reads the content of the file as a single string.
     *
     * @return the entire file content
     * @throws IOException if the file cannot be read
     */
    public String readFile() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(information.getS1()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    /**
     * Creates the file or directory if it does not exist.
     *
     * @param fileType the type to create (FILE or DIRECTORY)
     * @return {@code true} if the file or directory was created
     * @throws IOException if file creation fails
     */
    public boolean createFile(FileType fileType) throws IOException {
        Objects.requireNonNull(fileType, "FileType cannot be null");
        File target = information.getS1();
        if (target.exists()) return false;

        return switch (fileType) {
            case DIRECTORY -> target.mkdirs();
            case FILE -> target.createNewFile();
        };
    }

    /**
     * Validates whether a given string matches a regex pattern.
     *
     * @param text  the input string
     * @param regex the regular expression
     * @return {@code true} if it matches
     */
    @Contract(pure = true)
    public static boolean validateByRegularExpression(@NotNull String text, String regex) {
        return text.matches(regex);
    }

    /**
     * Replaces parts of the input text that match the given regex with the replacement.
     *
     * @param text        original string
     * @param regex       pattern to match
     * @param replacement replacement string
     * @return the modified string
     */
    @Contract(pure = true)
    public static @NotNull String replaceByRegularExpression(@NotNull String text, String regex, String replacement) {
        return text.replaceAll(regex, replacement);
    }

    /**
     * Finds all words in a given string that match a specific regular expression.
     *
     * @param text  the input string
     * @param regex the regex pattern to match
     * @return a list of matching words
     */
    public List<String> findWords(@NotNull String text, String regex) {
        List<String> matches = new ArrayList<>();
        information.setArray(text.split("( +|[,.;])"));
        Arrays.stream(information.getArray())
                .filter(word -> validateByRegularExpression(word, regex))
                .forEach(matches::add);
        return matches;
    }

    /**
     * Lists the file and directory names at the current path if it is a directory.
     *
     * @return an array of file or directory names
     */
    public String[] listFiles() {
        File file = information.getS1();
        return file.isDirectory() ? Objects.requireNonNullElse(file.list(), new String[0]) : new String[0];
    }

    /**
     * Lists only files or directories from the current path based on the given {@code FileType}.
     *
     * @param fileType type to filter by (FILE or DIRECTORY)
     * @return list of matching entries
     */
    public List<String> listFilesOnDirectory(FileType fileType) {
        Objects.requireNonNull(fileType, "FileType cannot be null");
        information.setArray(listFiles());
        if (information.getArray().length == 0) return information.getList();

        Arrays.stream(information.getArray()).forEach(name -> {
            File file = new File(information.getS1(), name);
            boolean isMatch = switch (fileType) {
                case DIRECTORY -> file.isDirectory();
                case FILE -> file.isFile();
            };
            if (isMatch) information.addElement(name);
        });

        return information.getList();
    }

    /**
     * Opens a file chooser dialog to select a file of the given extension.
     *
     * @param frame     the parent JFrame
     * @param extension the required file extension (e.g. "txt")
     * @return {@code true} if a file was selected
     */
    public boolean getFileFromFileChooser(JFrame frame, String extension) {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileNameExtensionFilter(extension + " files", extension));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            information.setS1(chooser.getSelectedFile());
            return true;
        }
        return false;
    }

    /**
     * Gets the current managed file.
     *
     * @return the current {@code File}
     */
    public File getFile() {
        return information.getS1();
    }

    /**
     * Sets a new file to be managed.
     *
     * @param file the new {@code File}
     */
    public void setFile(File file) {
        information.setS1(file);
    }
}
