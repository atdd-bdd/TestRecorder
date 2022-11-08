package kenpugh.TestRecorder;


import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readString;


public class MyFileSystem {
    public static void create(MyString filePath, String contents) {
        Path path = Paths.get(Configuration.rootFilePath.toString(), filePath.toString());
        try {
            FileWriter fw = new FileWriter(path.toString());
            fw.write(contents);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot access file  " + path);
        }
    }

    public static String read(MyString filePath) {
        Path path = Paths.get(Configuration.rootFilePath.toString(), filePath.toString());
        String text;
        try {
            text = readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot access file  " + path);
        }
        return text;
    }
}
