package com.kenpugh.testrecorder.domainterms;

import com.kenpugh.testrecorder.log.Log;

public enum TestStatus {
    Active, Inactive, Retired ;

    public static TestStatus parse(String value) {
        if (value.isBlank() || value.isBlank())
            value = TestStatus.Active.toString();
        TestStatus result = TestStatus.Active;
        try {
            result = TestStatus.valueOf(value);

        } catch (IllegalArgumentException e) {
            Log.write(Log.Level.Severe,"Bad TestStatus value ", value);
        }
        return result;
    }
}
