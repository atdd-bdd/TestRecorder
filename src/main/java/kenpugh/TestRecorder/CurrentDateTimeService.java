package kenpugh.TestRecorder;

public class CurrentDateTimeService {
    public static MyDateTime getCurrentDateTime() {
        MyDateTime result;
        if (Configuration.useTestDoubleForDateTime)
            result = Configuration.valueTestDoubleForDateTime;
        else
            result = new MyDateTime();
        return result;
    }
}
