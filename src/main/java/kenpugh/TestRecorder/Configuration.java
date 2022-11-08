package kenpugh.TestRecorder;

public class Configuration {
    static public MyString rootFilePath = new MyString();
    static public boolean useTestDoubleForDateTime = false;
    static public boolean useTestDoubleForRunner = false;
    static public MyDateTime valueTestDoubleForDateTime = new MyDateTime();
    static public Name valueTestDoubleForRunner = new Name();
    static void fromDTO(){
        String s = ConfigurationDTO.values.get("rootFilePath");
        if (s != null) rootFilePath = new MyString(s);
        s = ConfigurationDTO.values.get("useTestDoubleForDateTime");
        if (s!= null) useTestDoubleForDateTime = Boolean.parseBoolean(s);
        s = ConfigurationDTO.values.get("useTestDoubleForRunner");
        if (s!= null) useTestDoubleForRunner = Boolean.parseBoolean(s);
        s = ConfigurationDTO.values.get("valueTestDoubleForDateTime");
        if (s!=null) valueTestDoubleForDateTime = new MyDateTime(s);
        s= ConfigurationDTO.values.get("valueTestDoubleForRunner");
        if (s!=null) valueTestDoubleForRunner = new Name(s);
    }
    static void toDTO()
    {
        ConfigurationDTO.values.clear();
        ConfigurationDTO.values.put("rootFilePath", rootFilePath.toString());
        ConfigurationDTO.values.put("useTestDoubleForDateTime", Boolean.toString(useTestDoubleForDateTime));
        ConfigurationDTO.values.put("useTestDoubleForRunner", Boolean.toString(useTestDoubleForRunner));
        ConfigurationDTO.values.put("valueTestDoubleForDateTime", valueTestDoubleForDateTime.toString());
        ConfigurationDTO.values.put("valueTestDoubleForRunner", valueTestDoubleForRunner.toString());
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
