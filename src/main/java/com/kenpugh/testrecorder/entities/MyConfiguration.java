package com.kenpugh.testrecorder.entities;


import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.domainterms.MyFileSystem;
import com.kenpugh.testrecorder.domainterms.MyString;
import com.kenpugh.testrecorder.domainterms.Name;
import com.kenpugh.testrecorder.log.Log;
import com.kenpugh.testrecorder.os.EnvironmentVariables;

import java.util.Map;

public class MyConfiguration {
    public static final String rootFilePathString = MyConfigurationVariables.rootFilePath.toString();
    public static final String useTestDoubleForDateTimeString = MyConfigurationVariables.useTestDoubleForDateTime.toString();
    public static final String useTestDoubleForRunnerString = MyConfigurationVariables.useTestDoubleForRunner.toString();
    public static final String valueTestDoubleForDateTimeString = MyConfigurationVariables.valueTestDoubleForDateTime.toString();
    public static final String valueTestDoubleForRunnerString = MyConfigurationVariables.valueTestDoubleForRunner.toString();
    public static final String formNotCloseOnExitString = MyConfigurationVariables.formNotCloseOnExit.toString();
    public static final String databaseURLString = MyConfigurationVariables.databaseURL.toString();
    public static final String databaseJDBCDriverString = MyConfigurationVariables.databaseJDBCDriver.toString();
    public static final String databasePasswordString = MyConfigurationVariables.databasePassword.toString();
    public static final String databaseUserIDString = MyConfigurationVariables.databaseUserID.toString();
    public static MyString rootFilePath = new MyString();
    public static boolean useTestDoubleForDateTime = false;
    static public boolean useTestDoubleForRunner = false;
    static public boolean formNotCloseOnExit = false;
    static public MyDateTime valueTestDoubleForDateTime = new MyDateTime();
    static public Name valueTestDoubleForRunner = new Name();
    static public String databaseURL = "";
    static public String databaseJDBCDriver = "";
    static public String databaseUserID = "";
    static public String databasePassword = "";

    public static boolean isValidVariable(String variable) {
        try {
            MyConfigurationVariables.valueOf(variable);
        } catch (IllegalArgumentException e) {
            Log.write(Log.Level.Info, " Invalid configuration variable ", variable);
            return false;
        }
        return true;
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    public static void fromDTO() {
        for (Map.Entry<String, String> entry : MyConfigurationDTO.values.entrySet()) {
            String value = entry.getValue();
            String name = entry.getKey();
            if (value == null || name == null) {
                Log.write(Log.Level.Info, " Configuration null for value or name", name + " " + value);
                continue;
            }

            if (name.equals(rootFilePathString))
                rootFilePath = new MyString(value);
            else if (name.equals(useTestDoubleForRunnerString))
                useTestDoubleForRunner = Boolean.parseBoolean(value);
            else if (name.equals(useTestDoubleForDateTimeString))
                useTestDoubleForDateTime = Boolean.parseBoolean(value);
            else if (name.equals(valueTestDoubleForDateTimeString))
                valueTestDoubleForDateTime = new MyDateTime(value);
            else if (name.equals(valueTestDoubleForRunnerString))
                valueTestDoubleForRunner = new Name(value);
            else if (name.equals(formNotCloseOnExitString))
                formNotCloseOnExit = Boolean.parseBoolean(value);
            else if (name.equals(databaseURLString))
                databaseURL = new String(value);
            else if (name.equals(databaseJDBCDriverString))
                databaseJDBCDriver = new String(value);
            else if (name.equals(databasePasswordString))
                databasePassword = new String(value);
            else if (name.equals(databaseUserIDString))
                databaseUserID = new String(value);
            else
                Log.write(Log.Level.Severe, " Configuration bad variable", name);
        }
    }

    static void toDTO() {
        MyConfigurationDTO.values.clear();
        MyConfigurationDTO.values.put(rootFilePathString, rootFilePath.toString());
        MyConfigurationDTO.values.put(useTestDoubleForDateTimeString, Boolean.toString(useTestDoubleForDateTime));
        MyConfigurationDTO.values.put(useTestDoubleForRunnerString, Boolean.toString(useTestDoubleForRunner));
        MyConfigurationDTO.values.put(valueTestDoubleForDateTimeString, valueTestDoubleForDateTime.toString());
        MyConfigurationDTO.values.put(valueTestDoubleForRunnerString, valueTestDoubleForRunner.toString());
        MyConfigurationDTO.values.put(formNotCloseOnExitString, Boolean.toString(formNotCloseOnExit));
        MyConfigurationDTO.values.put(databaseURLString, databaseURL);
        MyConfigurationDTO.values.put(databaseJDBCDriverString, databaseJDBCDriver);
        MyConfigurationDTO.values.put(databasePasswordString, databasePassword);
        MyConfigurationDTO.values.put(databaseUserIDString, databaseUserID);
    }

    static private MyString getConfigurationFileName() {
        String environmentVariableName = "TEST_RECORDER_CONFIGURATION_PATH";
        MyString configurationFileName;
        String value = EnvironmentVariables.getenv(environmentVariableName);
        if (value.equals(EnvironmentVariables.NOT_FOUND))
            configurationFileName = new MyString("configuration.txt");
        else {
            configurationFileName = new MyString(value);
        }
        return configurationFileName;
    }

    static public void saveToFile() {
        toDTO();
        String out = MyConfigurationDTO.toSaveString();
        // Need to use blank root, so can read without rootFilePath being set
        MyString rootFilePathSaved = rootFilePath;
        rootFilePath = new MyString("");
        MyFileSystem.create(getConfigurationFileName(), out);

        rootFilePath = rootFilePathSaved;
    }

    static public void loadFromFile() {
        rootFilePath = new MyString("");
        String in = MyFileSystem.read(getConfigurationFileName());
        MyConfigurationDTO.fromSaveString(in);
        fromDTO();


    }

    @Override
    public String toString() {
        return "Configuration{ " + rootFilePathString + "=" + rootFilePath +
                " " + useTestDoubleForDateTimeString + "=" + useTestDoubleForDateTime +
                " " + useTestDoubleForRunnerString + "=" + useTestDoubleForRunner +
                " " + valueTestDoubleForDateTimeString + "=" + valueTestDoubleForDateTime
                + " " + valueTestDoubleForRunnerString + "=" + valueTestDoubleForRunner
                + formNotCloseOnExitString + "=" + formNotCloseOnExit
                + databaseURLString + "=" + databaseURL + " "
                + databaseJDBCDriverString + "=" + databaseJDBCDriver + " "
                + databasePasswordString + "=" + databasePassword + " "
                + databaseUserIDString + "=" + databaseUserID +

                " " + "}";
    }

    public enum MyConfigurationVariables {
        rootFilePath, useTestDoubleForDateTime,
        useTestDoubleForRunner, valueTestDoubleForDateTime,
        valueTestDoubleForRunner, formNotCloseOnExit,
        databaseURL, databaseJDBCDriver, databasePassword, databaseUserID
    }
}
