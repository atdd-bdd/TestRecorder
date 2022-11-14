package kenpugh.TestRecorder.OS;

import kenpugh.TestRecorder.Log.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentVariables {
    static Map<String, String > environment = new HashMap<>();


    public static void  setenv(String variable, String value){
        // Does this locally for testing
        // Must set env before running producion code
       environment.put(variable, value);
    }
    public static final String NOT_FOUND = "NOT FOUND";
    public static String getenv(String variable){
        String value = environment.get(variable);
        if (value == null){
            value = System.getenv(variable);
        }
        if (value == null) {
            Log.write(Log.Level.Info,
                    "** getenv variable not found ",
                    variable);
            value = NOT_FOUND;
        }
        return value;
    }
}
