package io.github.imecuadorian;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Files {

    private static final String SPLIT_CHARACTERS = "( +|[,.;])";
    private final GenericImpl<String, File> data;
    public Files(String pathname) {
        this.data = new GenericImpl<>(pathname, new File(pathname));
    }

    public void writeFile(String string, boolean append) throws IOException {
        FileWriter fileWriter = new FileWriter(data.getS1().getAbsolutePath(), !append);
        fileWriter.write(string);
        fileWriter.close();
    }

    public String readFile() throws IOException {
        data.setT2("");
        FileReader fileReader = new FileReader(data.getS1().getAbsolutePath());
        int character;
        while ((character = fileReader.read()) != -1) {
            data.setT2(data.getT2() + String.valueOf((char)character));
        }
        fileReader.close();
        return data.getT2();
    }

    public boolean create(Type type) {
        return switch (type) {
            case DIRECTORY -> {
                if (!data.getS1().exists()) {
                    yield data.getS1().mkdir();
                } else {
                    yield false;
                }
            }
            case FILE -> {
                if (!data.getS1().exists()) {
                    try {
                        yield data.getS1().createNewFile();
                    } catch (IOException e) {
                        yield false;
                    }
                } else {
                    yield false;
                }
            }
            case null -> false;
        };
    }

    public List<String> findWords(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        data.setArrayT(string.split("(\s+|[,.;])"));
        for (String word : data.getArrayT()) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                data.addElementListT(matcher.group());
            }
        }
        return data.getTList();
    }

    public String[] listFiles() {
        if (data.getS1().isDirectory()) {
            return data.getS1().list();
        }
        return new String[]{};
    }

    public List<String> listFileInDirectory(Type type) {
        data.setArrayT(listFiles());
        if (data.getArrayT().length == 0) {
            return new ArrayList<>();
        }
        for (String file : data.getArrayT()) {
            data.setS2(new File(data.getS1().getAbsolutePath() + File.separator + file));
            if (type == Type.DIRECTORY) {
                if (data.getS2().isDirectory()) {
                    data.addElementListT(file);
                }
            } else {
                if (data.getS2().isFile()) {
                    data.addElementListT(file);
                }
            }
        }
        return data.getTList();
    }

    public boolean getFileChooser(JFrame jFrame, String extension) {
        JFileChooser file = new JFileChooser();
        file.setAcceptAllFileFilterUsed(false);
        file.addChoosableFileFilter(new FileNameExtensionFilter(extension + "files", extension));
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int state = file.showOpenDialog(jFrame);
        if (state == JFileChooser.APPROVE_OPTION) {
            setFile(new File(file.getSelectedFile().getAbsolutePath()));
            return true;
        }
        return false;
    }

    public static boolean validateByRegularExpression(String string, String regex) {
        return string.matches(regex);
    }

    public static String replaceString(String string, String replace, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.replaceAll(replace);
    }

    public File getFile() {
        return data.getS1();
    }

    public void setFile(File file) {
        data.setS1(file);
    }

}
