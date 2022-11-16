package com.kenpugh.testrecorder.domainterms;

public enum TestResult {
    Success, Failure;

    public static TestResult parse(String lastResult) {
        if (lastResult.isBlank() || lastResult.isBlank())
            lastResult = TestResult.Failure.toString();
        TestResult result = TestResult.Failure;
        try {
            result = TestResult.valueOf(lastResult);

        } catch (IllegalArgumentException e) {
            System.err.println("Bad TestResult value " + lastResult);
        }
        return result;
    }
}
