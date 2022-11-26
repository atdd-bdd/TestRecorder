package com.kenpugh.testrecorder.os;

import com.kenpugh.testrecorder.log.Log;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentVariables {
    public static final String NOT_FOUND = "NOT FOUND";
    static final Map<String, String> environment = new HashMap<>();

    public static void setenv(String variable, String value) {
        // Does this locally for testing
        // Must set env before running production code
        environment.put(variable, value);
    }

    public static String getenv(String variable) {
        String value = environment.get(variable);
        if (value == null) {
            value = System.getenv(variable);
        }
        if (value == null) {
            Log.write(Log.Level.Info,
                    "getenv variable not found ",
                    variable);
            value = NOT_FOUND;
        }
        return value;
    }
}
