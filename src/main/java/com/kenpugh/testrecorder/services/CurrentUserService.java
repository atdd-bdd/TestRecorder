package com.kenpugh.testrecorder.services;

import com.kenpugh.testrecorder.domainterms.Name;
import com.kenpugh.testrecorder.entities.MyConfiguration;

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
