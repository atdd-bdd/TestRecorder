package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.DomainTerms.MyFileSystem;
import kenpugh.TestRecorder.DomainTerms.MyString;
import kenpugh.TestRecorder.DomainTerms.Name;

public class Configuration {
    public enum ConfigurationVariables {
        rootFilePath, useTestDoubleForDateTime,
        useTestDoubleForRunner, valueTestDoubleForDateTime,
        valueTestDoubleForRunner, formNotCloseOnExit
    }

    public static boolean isValidVariable(String variable) {
        try {
            ConfigurationVariables.valueOf(variable);
        } catch (IllegalArgumentException e) {
            System.out.println(" Invalid configuration variable ");
            return false;
        }
        return true;
    }

    public static final String rootFilePathString = ConfigurationVariables.rootFilePath.toString();

    public static final String useTestDoubleForDateTimeString = ConfigurationVariables.useTestDoubleForDateTime.toString();

    public static final String useTestDoubleForRunnerString = ConfigurationVariables.useTestDoubleForRunner.toString();

    public static final String valueTestDoubleForDateTimeString = ConfigurationVariables.valueTestDoubleForDateTime.toString();
    public static final String valueTestDoubleForRunnerString = ConfigurationVariables.valueTestDoubleForRunner.toString();
    public static final String formNotCloseOnExitString = ConfigurationVariables.formNotCloseOnExit.toString();
    public static MyString rootFilePath = new MyString();
    public static boolean useTestDoubleForDateTime = false;
    static public boolean useTestDoubleForRunner = false;
    static public boolean formNotCloseOnExit = false;

    static public MyDateTime valueTestDoubleForDateTime = new MyDateTime();
    static public Name valueTestDoubleForRunner = new Name();

    public static void fromDTO() {
        String s = ConfigurationDTO.values.get(rootFilePathString);
        if (s != null) rootFilePath = new MyString(s);
        s = ConfigurationDTO.values.get(useTestDoubleForDateTimeString);
        if (s != null) useTestDoubleForDateTime = Boolean.parseBoolean(s);
        s = ConfigurationDTO.values.get(useTestDoubleForRunnerString);
        if (s != null) useTestDoubleForRunner = Boolean.parseBoolean(s);
        s = ConfigurationDTO.values.get(valueTestDoubleForDateTimeString);
        if (s != null) valueTestDoubleForDateTime = new MyDateTime(s);
        s = ConfigurationDTO.values.get(valueTestDoubleForRunnerString);
        if (s != null) valueTestDoubleForRunner = new Name(s);
        s = ConfigurationDTO.values.get(formNotCloseOnExitString);
        if (s != null) formNotCloseOnExit = Boolean.parseBoolean(s);

    }

    static void toDTO() {
        ConfigurationDTO.values.clear();
        ConfigurationDTO.values.put(rootFilePathString, rootFilePath.toString());
        ConfigurationDTO.values.put(useTestDoubleForDateTimeString, Boolean.toString(useTestDoubleForDateTime));
        ConfigurationDTO.values.put(useTestDoubleForRunnerString, Boolean.toString(useTestDoubleForRunner));
        ConfigurationDTO.values.put(valueTestDoubleForDateTimeString, valueTestDoubleForDateTime.toString());
        ConfigurationDTO.values.put(valueTestDoubleForRunnerString, valueTestDoubleForRunner.toString());
        ConfigurationDTO.values.put(formNotCloseOnExitString, Boolean.toString(formNotCloseOnExit));
    }

    static private final MyString configurationFileName = new MyString("C:\\Users\\KenV1\\IdeaProjects\\TestRecorder\\target\\configuration.txt");

    static public void saveToFile() {
        toDTO();
        String out = ConfigurationDTO.toSaveString();
        System.out.println("*** Saving configuration " + out);
        // Need to use blank root, so can read without rootFilePath being set
        MyString rootFilePathSaved = rootFilePath;
        rootFilePath = new MyString("");
        MyFileSystem.create(configurationFileName, out);
        rootFilePath = rootFilePathSaved;
    }

    static public void loadFromFile() {
        rootFilePath = new MyString("");
        String in = MyFileSystem.read(configurationFileName);
        System.out.println("Loading configuration " + in);
        ConfigurationDTO.fromSaveString(in);
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
