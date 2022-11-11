package kenpugh.TestRecorder.DomainTerms;

public enum TestResult {
    Success, Failure;

    public static TestResult parse(String lastResult) {
        TestResult result = TestResult.Failure;
        try {
            result = TestResult.valueOf(lastResult);

        }
        catch (IllegalArgumentException e) {
            System.err.println("Bad TestResult value " +lastResult);
        }
        return result;
    }
}
