package kenpugh.TestRecorder.Services;

import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.Entities.MyConfiguration;

import java.util.Date;

public class CurrentDateTimeService {
    public static MyDateTime getCurrentDateTime() {
        MyDateTime result;
        if (MyConfiguration.useTestDoubleForDateTime)
            result = MyConfiguration.valueTestDoubleForDateTime;
        else
            result = new MyDateTime(new Date());
        return result;
    }
}
