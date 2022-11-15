package kenpugh.TestRecorder.services;

import kenpugh.TestRecorder.domainTerms.MyDateTime;
import kenpugh.TestRecorder.entities.MyConfiguration;

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
