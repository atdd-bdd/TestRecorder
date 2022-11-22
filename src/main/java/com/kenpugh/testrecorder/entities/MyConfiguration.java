package com.kenpugh.testrecorder.entities;

import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.domainterms.MyFileSystem;
import com.kenpugh.testrecorder.domainterms.MyString;
import com.kenpugh.testrecorder.domainterms.Name;
import com.kenpugh.testrecorder.log.Log;
import com.kenpugh.testrecorder.os.EnvironmentVariables;
public class MyConfiguration {
    public enum MyConfigurationVariables {
        rootFilePath, useTestDoubleForDateTime,
        useTestDoubleForRunner, valueTestDoubleForDateTime,
        valueTestDoubleForRunner, formNotCloseOnExit,
        databaseURL, databaseJDBCDriver, databasePassword, databaseUserID
    }

    public static boolean isValidVariable(String variable) {
        try {
            MyConfigurationVariables.valueOf(variable);
        } catch (IllegalArgumentException e) {
            Log.write(Log.Level.Info, " Invalid configuration variable ", variable);
            return false;
        }
        return true;
    }

    public static final String rootFilePathString = MyConfigurationVariables.rootFilePath.toString();

    public static final String useTestDoubleForDateTimeString = MyConfigurationVariables.useTestDoubleForDateTime.toString();

    public static final String useTestDoubleForRunnerString = MyConfigurationVariables.useTestDoubleForRunner.toString();

    public static final String valueTestDoubleForDateTimeString = MyConfigurationVariables.valueTestDoubleForDateTime.toString();
    public static final String valueTestDoubleForRunnerString = MyConfigurationVariables.valueTestDoubleForRunner.toString();
    public static final String formNotCloseOnExitString = MyConfigurationVariables.formNotCloseOnExit.toString();

    @SuppressWarnings("unused")
    public static final String databaseURLString = MyConfigurationVariables.databaseURL.toString();
    @SuppressWarnings("unused")
    public static final String databaseJDBCDriverString = MyConfigurationVariables.databaseJDBCDriver.toString();
    @SuppressWarnings("unused")
    public static final String databasePasswordString = MyConfigurationVariables.databasePassword.toString();
    @SuppressWarnings("unused")
    public static final String databaseUserIDString = MyConfigurationVariables.databaseUserID.toString();

    public static MyString rootFilePath = new MyString();
    public static boolean useTestDoubleForDateTime = false;
    static public boolean useTestDoubleForRunner = false;
    static public boolean formNotCloseOnExit = false;

    static public MyDateTime valueTestDoubleForDateTime = new MyDateTime();
    static public Name valueTestDoubleForRunner = new Name();

    static public String databaseURL;
    static public String databaseJDBCDriver;
    static public String databaseUserID;
    static public String databasePassword;

    @SuppressWarnings("StringOperationCanBeSimplified")
    public static void fromDTO() {
        String s = MyConfigurationDTO.values.get(rootFilePathString);
        if (s != null) rootFilePath = new MyString(s);
        s = MyConfigurationDTO.values.get(useTestDoubleForDateTimeString);
        if (s != null) useTestDoubleForDateTime = Boolean.parseBoolean(s);
        s = MyConfigurationDTO.values.get(useTestDoubleForRunnerString);
        if (s != null) useTestDoubleForRunner = Boolean.parseBoolean(s);
        s = MyConfigurationDTO.values.get(valueTestDoubleForDateTimeString);
        if (s != null) valueTestDoubleForDateTime = new MyDateTime(s);
        s = MyConfigurationDTO.values.get(valueTestDoubleForRunnerString);
        if (s != null) valueTestDoubleForRunner = new Name(s);
        s = MyConfigurationDTO.values.get(formNotCloseOnExitString);
        if (s != null) formNotCloseOnExit = Boolean.parseBoolean(s);
        s = MyConfigurationDTO.values.get(databaseURLString);
        if (s != null) databaseURL = new String(s);
        s = MyConfigurationDTO.values.get(databaseJDBCDriverString);
        if (s != null) databaseJDBCDriver = new String(s);
        s = MyConfigurationDTO.values.get(databasePasswordString);
        if (s != null) databasePassword = new String(s);
        else databasePassword = "";
        s = MyConfigurationDTO.values.get(databaseUserIDString);
        if (s != null) databaseUserID = new String(s);

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

     static private MyString getConfigurationFileName ()
    {
        String environmentVariableName = "TEST_RECORDER_CONFIGURATION_PATH";
        MyString configurationFileName;
        String value = EnvironmentVariables.getenv(environmentVariableName);
         if (value.equals(EnvironmentVariables.NOT_FOUND))
         configurationFileName =  new MyString("C:\\Users\\KenV1\\IdeaProjects\\TestRecorder\\target\\configuration.txt");
        else
         {
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
        MyFileSystem.create(getConfigurationFileName(),out);

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
        return "Configuration{ " + "rootFilePath=" + rootFilePath + " " + "useTestDoubleForDateTime=" +
                useTestDoubleForDateTime + " " + "useTestDoubleForRunner=" + useTestDoubleForRunner + " " +
                "valueTestDoubleForDateTime=" + valueTestDoubleForDateTime
                + " " + "valueTestDoubleForRunner=" + valueTestDoubleForRunner
                + formNotCloseOnExitString + "=" + formNotCloseOnExit
                + databaseURLString + "=" + databaseURL + " "
                + databaseJDBCDriverString + "=" + databaseJDBCDriver +" "
                + databasePasswordString + "=" + databasePassword +" "
                + databaseUserIDString + "=" + databaseUserID +

                " " + "}";
    }
}
