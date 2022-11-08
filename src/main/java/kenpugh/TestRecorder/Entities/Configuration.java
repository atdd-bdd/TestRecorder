package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.*;

public class Configuration {
    public enum ConfigurationVariables { rootFilePath, useTestDoubleForDateTime,
        useTestDoubleForRunner, valueTestDoubleForDateTime, valueTestDoubleForRunner};
    public static boolean isValidVariable(String variable){
        int count = ConfigurationVariables.values().length;
        try {
            ConfigurationVariables cv = ConfigurationVariables.valueOf(variable);
        }
            catch (IllegalArgumentException e) {
                System.out.println(" Invalid configuration variable ");
                return false;
            }
    return true;
    }

    static String rootFilePathString = ConfigurationVariables.rootFilePath.toString();

    static String useTestDoubleForDateTimeString = ConfigurationVariables.useTestDoubleForDateTime.toString();

    static String useTestDoubleForRunnerString = ConfigurationVariables.useTestDoubleForRunner.toString();

    static String valueTestDoubleForDateTimeString = ConfigurationVariables.valueTestDoubleForDateTime.toString();
    static String valueTestDoubleForRunnerString = ConfigurationVariables.valueTestDoubleForRunner.toString();
    static public MyString rootFilePath = new MyString();
    static public boolean useTestDoubleForDateTime = false;
    static public boolean useTestDoubleForRunner = false;

    static public MyDateTime valueTestDoubleForDateTime = new MyDateTime();
    static public Name valueTestDoubleForRunner = new Name();
    public static void fromDTO(){
        String s = ConfigurationDTO.values.get(rootFilePathString);
        if (s != null) rootFilePath = new MyString(s);
        s = ConfigurationDTO.values.get(useTestDoubleForDateTimeString);
        if (s!= null) useTestDoubleForDateTime = Boolean.parseBoolean(s);
        s = ConfigurationDTO.values.get(useTestDoubleForRunnerString);
        if (s!= null) useTestDoubleForRunner = Boolean.parseBoolean(s);
        s = ConfigurationDTO.values.get(valueTestDoubleForDateTimeString);
        if (s!=null) valueTestDoubleForDateTime = new MyDateTime(s);
        s= ConfigurationDTO.values.get(valueTestDoubleForRunnerString);
        if (s!=null) valueTestDoubleForRunner = new Name(s);
    }
    static void toDTO()
    {
        ConfigurationDTO.values.clear();
        ConfigurationDTO.values.put(rootFilePathString, rootFilePath.toString());
        ConfigurationDTO.values.put(useTestDoubleForDateTimeString, Boolean.toString(useTestDoubleForDateTime));
        ConfigurationDTO.values.put(useTestDoubleForRunnerString, Boolean.toString(useTestDoubleForRunner));
        ConfigurationDTO.values.put(valueTestDoubleForDateTimeString, valueTestDoubleForDateTime.toString());
        ConfigurationDTO.values.put(valueTestDoubleForRunnerString, valueTestDoubleForRunner.toString());
    }
    static private final MyString  configurationFileName = new MyString("C:\\Users\\KenV1\\IdeaProjects\\TestRecorder\\target\\configuration.txt");
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
        String in =  MyFileSystem.read(configurationFileName);
        System.out.println("Loading configuration " + in);
        ConfigurationDTO.fromSaveString(in);
        fromDTO();


    }

    @Override
    public String toString() {
        return "Configuration{ " + "rootFilePath=" + rootFilePath + " " + "useTestDoubleForDateTime="+
                useTestDoubleForDateTime + " " + "useTestDoubleForRunner=" + useTestDoubleForRunner + " " +
                "valueTestDoubleForDateTime=" + valueTestDoubleForDateTime + " " + "valueTestDoubleForRunner=" +
                valueTestDoubleForRunner +" }";
      }
}
