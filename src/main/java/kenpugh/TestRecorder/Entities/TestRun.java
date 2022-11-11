package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.*;
import kenpugh.TestRecorder.Services.CurrentDateTimeService;
import kenpugh.TestRecorder.Services.CurrentUserService;

import java.util.Objects;

public class TestRun {
    private IssueID issueID = new IssueID();
    private MyDateTime dateTime = new MyDateTime();
    private TestResult testResult = TestResult.Failure;
    private MyString comments = new MyString();
    private Name runner = new Name();

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
        testRunDTO.testResult = testResult.toString();
        return testRunDTO;
    }

    @Override
    public String toString() {
        return "TestRun{" +
                "issueID=" + issueID +
                ", dateTime=" + dateTime +
                ", testResult=" + testResult +
                ", comments=" + comments +
                ", runner=" + runner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestRun testRun = (TestRun) o;
        return issueID.equals(testRun.issueID) && dateTime.equals(testRun.dateTime) && testResult == testRun.testResult && comments.equals(testRun.comments) && runner.equals(testRun.runner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueID, dateTime, testResult, comments, runner);
    }

    public void fromDTO(TestRunDTO testRunDTO) {
        if (testRunDTO.comments != null) comments = new MyString(testRunDTO.comments);
        if (testRunDTO.issueID != null) issueID = new IssueID(testRunDTO.issueID);
        if (testRunDTO.dateTime != null) dateTime = new MyDateTime(testRunDTO.dateTime);
        if (testRunDTO.runner != null) runner = new Name(testRunDTO.runner);
        if (testRunDTO.testResult != null) testResult = TestResult.valueOf(testRunDTO.testResult);
    }

    static public TestRun TestRunFromDTO(TestRunDTO testRunDTO) {
        TestRun result = new TestRun();
        result.fromDTO(testRunDTO);
        return result;
    }

    public TestResult getTestResult() {
        return testResult;
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
