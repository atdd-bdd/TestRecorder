package com.kenpugh.testrecorder.entities;

import java.util.HashMap;
import java.util.Map;

public class MyConfigurationDTO {
    public static final Map<String, String> values = new HashMap<>();

    public static void addToMap(String variable, String value) {
        values.put(variable, value);
    }

    public static String toSaveString() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            out.append(entry.getKey());
            out.append("\t");
            out.append(entry.getValue());
            out.append("\t");
            out.append("\n");
        }
        return out.toString();
    }

    public static void fromSaveString(String in) {
        String[] lines = in.split("\n");
        for (String l : lines) {
            String[] words = l.split("\t");
            if (words.length >= 2)
                values.put(words[0], words[1]);
        }
    }
}
