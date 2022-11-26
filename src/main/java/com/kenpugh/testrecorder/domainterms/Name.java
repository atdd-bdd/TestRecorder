package com.kenpugh.testrecorder.domainterms;

import com.kenpugh.testrecorder.log.Log;

import java.util.Objects;
import java.util.regex.Pattern;

public class Name {
    public static final String EMPTY = "";
    public static final String NOT_SPECIFIED = "Name Not Specified";
    static final Pattern patternWordCharsSpaceOnly = Pattern.compile("[^\\w ]",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private final String value;

    public Name() {
        this.value = NOT_SPECIFIED;
    }

    public Name
            (String value) {
        String newValue = patternWordCharsSpaceOnly.matcher(value).replaceAll("");
        if (!newValue.equals(value))
            Log.write(Log.Level.Severe, " Replaced ", value + " with " + newValue);
        this.value = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
