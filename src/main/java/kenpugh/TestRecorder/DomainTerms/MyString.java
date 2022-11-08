package kenpugh.TestRecorder.DomainTerms;

import java.util.Objects;

public class MyString {
    private final String value;

    public MyString() {
        value = "";
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

    public MyString(String value) {
        this.value = value;
    }
}
