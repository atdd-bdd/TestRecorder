package kenpugh.TestRecorder.services;

import kenpugh.TestRecorder.domainTerms.Name;
import kenpugh.TestRecorder.entities.MyConfiguration;

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
