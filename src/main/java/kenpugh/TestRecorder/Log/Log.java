package kenpugh.TestRecorder.Log;

public class Log {
   public  enum Level { Info, Debug, Severe}
    // public enum Message {BdaPath }  /// Add standard error messges here
   static public void  write(Level level, String message, String values){
        String out = message + " " + values;
        if (level == Level.Severe)
            System.err.println("***" + out);
        else
            System.out.println(level.toString() + out);

    }
}
