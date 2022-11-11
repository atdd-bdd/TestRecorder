package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.DomainTerms.MyFileSystem;
import kenpugh.TestRecorder.DomainTerms.MyString;
import kenpugh.TestRecorder.DomainTerms.Name;

public class MyConfiguration {
    public enum MyConfigurationVariables {
        rootFilePath, useTestDoubleForDateTime,
        useTestDoubleForRunner, valueTestDoubleForDateTime,
        valueTestDoubleForRunner, formNotCloseOnExit
    }

    public static boolean isValidVariable(String variable) {
        try {
            MyConfigurationVariables.valueOf(variable);
        } catch (IllegalArgumentException e) {
            System.err.println(" Invalid configuration variable ");
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
    public static MyString rootFilePath = new MyString();
    public static boolean useTestDoubleForDateTime = false;
    static public boolean useTestDoubleForRunner = false;
    static public boolean formNotCloseOnExit = false;

    static public MyDateTime valueTestDoubleForDateTime = new MyDateTime();
    static public Name valueTestDoubleForRunner = new Name();

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

    }

    static void toDTO() {
        MyConfigurationDTO.values.clear();
        MyConfigurationDTO.values.put(rootFilePathString, rootFilePath.toString());
        MyConfigurationDTO.values.put(useTestDoubleForDateTimeString, Boolean.toString(useTestDoubleForDateTime));
        MyConfigurationDTO.values.put(useTestDoubleForRunnerString, Boolean.toString(useTestDoubleForRunner));
        MyConfigurationDTO.values.put(valueTestDoubleForDateTimeString, valueTestDoubleForDateTime.toString());
        MyConfigurationDTO.values.put(valueTestDoubleForRunnerString, valueTestDoubleForRunner.toString());
        MyConfigurationDTO.values.put(formNotCloseOnExitString, Boolean.toString(formNotCloseOnExit));
    }

    static private final MyString configurationFileName = new MyString("C:\\Users\\KenV1\\IdeaProjects\\TestRecorder\\target\\configuration.txt");

    static public void saveToFile() {
        toDTO();
        String out = MyConfigurationDTO.toSaveString();
        // Need to use blank root, so can read without rootFilePath being set
        MyString rootFilePathSaved = rootFilePath;
        rootFilePath = new MyString("");
        MyFileSystem.create(configurationFileName, out);
        rootFilePath = rootFilePathSaved;
    }

    static public void loadFromFile() {
        rootFilePath = new MyString("");
        String in = MyFileSystem.read(configurationFileName);
        MyConfigurationDTO.fromSaveString(in);
        fromDTO();


    }

    @Override
    public String toString() {
        return "Configuration{ " + "rootFilePath=" + rootFilePath + " " + "useTestDoubleForDateTime=" +
                useTestDoubleForDateTime + " " + "useTestDoubleForRunner=" + useTestDoubleForRunner + " " +
                "valueTestDoubleForDateTime=" + valueTestDoubleForDateTime + " " + "valueTestDoubleForRunner=" +
                valueTestDoubleForRunner +
                formNotCloseOnExitString + "=" + formNotCloseOnExit + " " + "}";
    }
}
