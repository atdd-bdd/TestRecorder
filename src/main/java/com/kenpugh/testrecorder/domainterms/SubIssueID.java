package com.kenpugh.testrecorder.domainterms;



import java.util.Objects;

public class SubIssueID {
    private final String value;
    static public final String NOT_SPECIFIED = "000";
    @SuppressWarnings("unused")
    public static final SubIssueID INVALID_ISSUE_ID = new SubIssueID(NOT_SPECIFIED);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubIssueID issueID = (SubIssueID) o;
        return value.equals(issueID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public SubIssueID() {
        this.value = NOT_SPECIFIED;
    }

    public SubIssueID(String value) {
        if (CheckValue(value))
            this.value = value;
        else
            this.value = NOT_SPECIFIED;

    }

    @SuppressWarnings("RedundantIfStatement")
    static public  boolean CheckValue(String value) {
        if (value.length() != 3)
            return false;
        if (value.matches("\\w+"))
            return true;
        else
            return false;
    }
    @Override
    public String toString() {
        return value;
    }
}
