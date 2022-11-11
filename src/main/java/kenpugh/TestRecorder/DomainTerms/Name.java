package kenpugh.TestRecorder.DomainTerms;

import java.util.Objects;

public class Name {
    private final String value;
    public static final String NOT_SPECIFIED = "No Name";

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

    public Name() {
        this.value = NOT_SPECIFIED;
    }

    public Name
            (String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
