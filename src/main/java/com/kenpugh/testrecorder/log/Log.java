package com.kenpugh.testrecorder.log;

public class Log {
   public  enum Level { Info, @SuppressWarnings("unused") Debug, Severe}
    // public enum Message {BdaPath }  /// Add standard error messages here
   static public void  write(Level level, String message, String values){
        String out = message + " " + values;
        if (level == Level.Severe)
            System.err.println("****" + out);
        else if (level == Level.Debug)
        {
            //System.out.println(level + " " + out);
            System.out.println();
        }
        else {
            System.out.println();   // Ignore info for now
        }

    }
}
