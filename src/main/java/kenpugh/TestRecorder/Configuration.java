package kenpugh.TestRecorder;

public class Configuration {
    static String rootFilePath = "";
    static void setValue(String name, String value){
        if (name.equals("RootFilePath")){
            rootFilePath = value;
        }
    }
}
