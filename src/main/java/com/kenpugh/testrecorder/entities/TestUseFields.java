package com.kenpugh.testrecorder.entities;


@SuppressWarnings("CanBeFinal")
public class TestUseFields {
    public boolean issueID = false;
    public boolean name = false;
    public boolean lastResult = false;
    public boolean runner = false;
    public boolean dateLastRun = false;
    public boolean datePreviousResult = false;
    public boolean filePath = false;
    public boolean comments = false;
    public boolean subIssueID = false;
    public boolean testStatus = false;

    @Override
    public String toString() {
        return "TestUseFields{" +
                "issueID=" + issueID +
                ", name=" + name +
                ", lastResult=" + lastResult +
                ", runner=" + runner +
                ", dateLastRun=" + dateLastRun +
                ", datePreviousResult=" + datePreviousResult +
                ", filePath=" + filePath +
                ", comments=" + comments +
                ", subIssueID=" + subIssueID +
                ", testStatus=" + testStatus +
                '}';
    }

}