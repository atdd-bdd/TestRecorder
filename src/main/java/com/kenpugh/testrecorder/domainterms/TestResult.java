package com.kenpugh.testrecorder.domainterms;

import com.kenpugh.testrecorder.log.Log;

public enum TestResult {
    Success, Failure;

    public static TestResult parse(String value) {
        if (value.isBlank() || value.isBlank())
            value = TestResult.Failure.toString();
        TestResult result = TestResult.Failure;
        try {
            result = TestResult.valueOf(value);

        } catch (IllegalArgumentException e) {
            Log.write(Log.Level.Debug, "Bad TestResult value ", value);
        }
        return result;
    }
}
