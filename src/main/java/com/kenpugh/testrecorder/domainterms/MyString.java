package com.kenpugh.testrecorder.domainterms;

import com.kenpugh.testrecorder.log.Log;

import java.util.Objects;
import java.util.regex.Pattern;

public class MyString {
    static final Pattern patternPrintableCharsOnly = Pattern.compile("[^\\\\!#%&'*+,\\-./:;<=>?@^_`{|}~\\w\\s]",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private final String value;

    public MyString() {
        value = "";
    }

    public MyString(String value) {
        String newValue = patternPrintableCharsOnly.matcher(value).replaceAll("");
        if (!newValue.equals(value))
            Log.write(Log.Level.Severe, " Replaced ", value + " with " + newValue);
        this.value = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyString myString = (MyString) o;
        return value.equals(myString.value);
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