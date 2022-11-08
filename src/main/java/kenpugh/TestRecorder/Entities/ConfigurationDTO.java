package kenpugh.TestRecorder.Entities;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationDTO {
    public static Map<String, String> values = new HashMap<>();

    public static String toSaveString() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            out.append(entry.getKey());
            out.append("|");
            out.append(entry.getValue());
            out.append("$");
        }
        return out.toString();
    }

    public static void fromSaveString(String in) {
        String [] lines = in.split("\\$");
        for (String l : lines){
            System.out.println("line is " + l);
            String [] words = l.split("\\|");
            values.put(words[0], words[1]);
        }
    }
}
