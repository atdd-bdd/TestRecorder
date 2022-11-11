package kenpugh.TestRecorder.Entities;

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
            out.append("|");
            out.append(entry.getValue());
            out.append("|");
            out.append("$");
        }
        return out.toString();
    }

    public static void fromSaveString(String in) {
        String[] lines = in.split("\\$");
        for (String l : lines) {
            String[] words = l.split("\\|");
            if (words.length >= 2)
                values.put(words[0], words[1]);
        }
    }
}
