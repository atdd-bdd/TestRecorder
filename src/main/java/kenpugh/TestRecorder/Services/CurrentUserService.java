package kenpugh.TestRecorder.Services;

import kenpugh.TestRecorder.DomainTerms.Name;
import kenpugh.TestRecorder.Entities.Configuration;

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
