package com.kenpugh.testrecorder.domainterms;


import com.kenpugh.testrecorder.entities.MyConfiguration;
import com.kenpugh.testrecorder.log.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readString;


public class MyFileSystem {
    public static void create(MyString filePath, String contents) {
        Path path = Paths.get(MyConfiguration.rootFilePath.toString(), filePath.toString());
        try {
            FileWriter fw = new FileWriter(path.toString());
            fw.write(contents);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot access file  " + path);
        }
    }
    public static boolean checkReadabiity(MyString filePath) {
        Path path = Paths.get(MyConfiguration.rootFilePath.toString(), filePath.toString());
        try {
            readString(path);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    public static String read(MyString filePath) {
        Path path = Paths.get(MyConfiguration.rootFilePath.toString(), filePath.toString());
        Log.write(Log.Level.Debug, " ", "path is ||" + MyConfiguration.rootFilePath.toString() + "||" + filePath.toString());
        String text;
        try {
            text = readString(path);
        } catch (IOException e) {
            text = "";
            Log.write(Log.Level.Severe, "Cannot access file ", path.toString());
        }
        return text;
    }
}
