package kenpugh.TestRecorder.Services;

import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.Entities.Configuration;

import java.util.Date;

public class CurrentDateTimeService {
    public static MyDateTime getCurrentDateTime() {
        MyDateTime result;
        if (Configuration.useTestDoubleForDateTime)
            result = Configuration.valueTestDoubleForDateTime;
        else
            result = new MyDateTime(new Date());
        return result;
    }
}
