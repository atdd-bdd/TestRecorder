package com.kenpugh.testrecorder.services;

import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.entities.MyConfiguration;

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
