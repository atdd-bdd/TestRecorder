package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.DomainTerms.MyString;
import kenpugh.TestRecorder.DomainTerms.Name;
import kenpugh.TestRecorder.DomainTerms.TestResult;

import java.util.Objects;

public class TestRun {
    public IssueID issueID = new IssueID();
    public MyDateTime dateTime = new MyDateTime();
    TestResult testResult = TestResult.Failure;
    MyString comments = new MyString();
    public Name runner = new Name();

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
        System.out.println("*** TestRun is " + this);
    }
    static public TestRun TestRunFromDTO(TestRunDTO testRunDTO) {
        TestRun result = new TestRun();
        result.fromDTO(testRunDTO);
        return result;
    }
}