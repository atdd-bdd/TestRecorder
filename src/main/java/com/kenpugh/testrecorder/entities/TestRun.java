package com.kenpugh.testrecorder.entities;

import com.kenpugh.testrecorder.domainterms.*;

import com.kenpugh.testrecorder.services.CurrentDateTimeService;
import com.kenpugh.testrecorder.services.CurrentUserService;

import java.util.Objects;

public class TestRun {
    private IssueID issueID = new IssueID();
    private MyDateTime dateTime = new MyDateTime();
    private TestResult result = TestResult.Failure;
    private MyString comments = new MyString();
    private Name runner = new Name(Name.EMPTY);

    public static TestRun getBaseTestRun(IssueID issueID) {
        TestRun result = new TestRun();
        result.issueID = issueID;
        result.dateTime = CurrentDateTimeService.getCurrentDateTime();
        result.runner = CurrentUserService.getCurrentUser();
        return result;
    }

    public TestRunDTO getDTO() {
        TestRunDTO testRunDTO = new TestRunDTO();
        testRunDTO.comments = comments.toString();
        testRunDTO.issueID = issueID.toString();
        testRunDTO.dateTime = dateTime.toString();
        testRunDTO.runner = runner.toString();
        testRunDTO.result = result.toString();
        return testRunDTO;
    }

    @Override
    public String toString() {
        return "TestRun{" +
                "issueID=" + issueID +
                ", dateTime=" + dateTime +
                ", testResult=" + result +
                ", comments=" + comments +
                ", runner=" + runner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestRun testRun = (TestRun) o;
        return issueID.equals(testRun.issueID) && dateTime.equals(testRun.dateTime) && result == testRun.result && comments.equals(testRun.comments) && runner.equals(testRun.runner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueID, dateTime, result, comments, runner);
    }

    public void fromDTO(TestRunDTO testRunDTO) {
         comments = new MyString(testRunDTO.comments);
         issueID = new IssueID(testRunDTO.issueID);
         dateTime = new MyDateTime(testRunDTO.dateTime);
         runner = new Name(testRunDTO.runner);
         result = TestResult.valueOf(testRunDTO.result);
    }

    static public TestRun TestRunFromDTO(TestRunDTO testRunDTO) {
        TestRun result = new TestRun();
        result.fromDTO(testRunDTO);
        return result;
    }

    public TestResult getTestResult() {
        return result;
    }

    public MyDateTime getDateTime() {
        return dateTime;
    }

    public IssueID getIssueID() {
        return issueID;
    }

    public Name getRunner() {
        return runner;
    }

    public MyString getComments() {
        return comments;
    }

    public void setRunner(Name runner) {
        this.runner = runner;
    }

    public void setDateTime(MyDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setIssueID(IssueID issueID) {
        this.issueID = issueID;
    }
}
