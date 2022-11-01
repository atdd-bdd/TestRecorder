package kenpugh.TestRecorder;

import java.util.Objects;

public class IssueID {
    private final String value;

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

    static public final String NOT_SPECIFIED = "00000";
    public IssueID(){
        this.value = NOT_SPECIFIED;
    }
    public IssueID(String value){
        if (value.length() != 5)
            this.value = NOT_SPECIFIED;
        else {
            if (value.matches("\\w+"))
                this.value = value;
            else
                this.value = NOT_SPECIFIED;
        }
    }
    @Override
    public String toString(){
        return value;
    }
}
