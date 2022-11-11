package kenpugh.TestRecorder.Services;

import kenpugh.TestRecorder.DomainTerms.Name;
import kenpugh.TestRecorder.Entities.MyConfiguration;

public class CurrentUserService {
    public static Name getCurrentUser() {
        Name result;
        if (MyConfiguration.useTestDoubleForRunner)
            result = MyConfiguration.valueTestDoubleForRunner;
        else {
            result = new Name(System.getenv("USERNAME"));
        }
        return result;
    }
}
