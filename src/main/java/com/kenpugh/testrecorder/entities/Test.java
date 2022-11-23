package com.kenpugh.testrecorder.entities;

import com.kenpugh.testrecorder.domainterms.*;

import com.kenpugh.testrecorder.log.Log;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Test {
    private IssueID issueID = IssueID.INVALID_ISSUE_ID;
   private Name name = new Name("");
    private TestResult lastResult = TestResult.Failure;
    private Name runner = new Name("");
    private MyDateTime dateLastRun = MyDateTime.NEVER_DATETIME;
    private MyDateTime datePreviousResult = MyDateTime.NEVER_DATETIME;
    private MyString filePath = new MyString("File Path Not Specified");
    private TextString comments = new TextString("No comment");
    private SubIssueID subIssueID = new SubIssueID();
    private TestStatus testStatus = TestStatus.Active;
    public final static Test NOT_FOUND = new Test();
    public boolean selectiveEquals(Object o, TestUseFields testUseFields) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;

        boolean result = (issueID.equals(test.issueID) || !testUseFields.issueID)
                && (name.equals(test.name) || !testUseFields.name)
                && (lastResult == test.lastResult || !testUseFields.lastResult)
                && (runner.equals(test.runner)  || !testUseFields.runner)
                && (dateLastRun.equals(test.dateLastRun)  || !testUseFields.dateLastRun)
                && (datePreviousResult.equals(test.datePreviousResult) || !testUseFields.datePreviousResult)
                && (filePath.equals(test.filePath) || !testUseFields.filePath)
                && (comments.equals(test.comments) || !testUseFields.comments)
                && (subIssueID.equals(test.subIssueID) || !testUseFields.subIssueID)
                && (testStatus.equals(test.testStatus) || !testUseFields.testStatus)
                ;
    if (!result)
            Log.write(Log.Level.Debug, "Selective equal values " + " for " + this + " == " + test +
                    " selections: ", testUseFields.toString() );
    return result;
    }
    public void fromDTO(@NotNull TestDTO testDTO) {
         issueID = new IssueID(testDTO.issueID);
        name = new Name(testDTO.name);
         lastResult = TestResult.parse(testDTO.lastResult);
         dateLastRun = MyDateTime.parse(testDTO.dateLastRun);
         datePreviousResult = MyDateTime.parse(testDTO.datePreviousResult);
         filePath = new MyString(testDTO.filePath);
        comments = new TextString(testDTO.comments);
        runner = new Name(testDTO.runner);
        testStatus = TestStatus.parse(testDTO.testStatus);
        subIssueID = new SubIssueID(testDTO.subIssueID);
    }

    static public Test testFromDTO(TestDTO testDTO) {
        Test result = new Test();
        result.fromDTO((testDTO));
        return result;
    }

    public TestDTO getDTO() {
        TestDTO testDTO = new TestDTO();
        testDTO.issueID = issueID.toString();
        testDTO.name = name.toString();
        testDTO.runner = runner.toString();
        testDTO.dateLastRun = dateLastRun.toString();
        testDTO.datePreviousResult = datePreviousResult.toString();
        testDTO.filePath = filePath.toString();
        testDTO.comments = comments.toString();
        testDTO.lastResult = lastResult.toString();
        testDTO.subIssueID = subIssueID.toString();
        testDTO.testStatus = testStatus.toString();

        return testDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(issueID, test.issueID) && Objects.equals(name, test.name) && lastResult == test.lastResult && Objects.equals(runner, test.runner) && Objects.equals(dateLastRun, test.dateLastRun) && Objects.equals(datePreviousResult, test.datePreviousResult) && Objects.equals(filePath, test.filePath) && Objects.equals(comments, test.comments) && Objects.equals(subIssueID, test.subIssueID) && testStatus == test.testStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueID, name, lastResult, runner, dateLastRun, datePreviousResult, filePath, comments, subIssueID, testStatus);
    }

    @Override
    public String toString() {
        return "Test{" +
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




    public void UpdateTestFromTestRun(TestRun tr) {
        if (!tr.getIssueID().equals(issueID)) {
            Log.write(Log.Level.Severe," Trying to update wrong test " , issueID + " with " + tr.getIssueID());
            return;
        }

        runner = tr.getRunner();
        comments = tr.getComments();
        if (tr.getTestResult() != lastResult) {
            datePreviousResult = dateLastRun;
        }
        dateLastRun = tr.getDateTime();
        lastResult = tr.getTestResult();
    }

    public MyString getFilePath() {
        return filePath;
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }
}
