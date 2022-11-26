package com.kenpugh.testrecorder.domainterms;


import java.util.Objects;

public class IssueID {
    static public final String NOT_SPECIFIED = "00000";
    public static final IssueID INVALID_ISSUE_ID = new IssueID(NOT_SPECIFIED);
    private final String value;

    public IssueID() {
        this.value = NOT_SPECIFIED;
    }

    public IssueID(String value) {
        if (CheckValue(value))
            this.value = value;
        else
            this.value = NOT_SPECIFIED;
    }

    @SuppressWarnings("RedundantIfStatement")
    static public boolean CheckValue(String value) {
        if (value.length() != 5)
            return false;
        if (value.matches("\\w+"))
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueID issueID = (IssueID) o;
        return value.equals(issueID.value);
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
