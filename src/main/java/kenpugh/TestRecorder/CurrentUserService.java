package kenpugh.TestRecorder;

public class CurrentUserService {
    public static Name getCurrentUser() {
        Name result;
        if (Configuration.useTestDoubleForRunner)
            result = Configuration.valueTestDoubleForRunner;
        else {
            result = new Name(System.getenv("USERNAME"));
        }
        return result;
    }
}
