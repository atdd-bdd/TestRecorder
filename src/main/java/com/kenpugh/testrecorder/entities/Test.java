package com.kenpugh.testrecorder.entities;

import com.kenpugh.testrecorder.domainterms.*;

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
    private MyString comments = new MyString("No comment");
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
                && (comments.equals(test.comments) || !testUseFields.comments);
    if (!result)
            System.out.println("Selective equal values " + " for " + this + " == " + test + " selections: "
                    + testUseFields );
    return result;
    }
    public void fromDTO(@NotNull TestDTO testDTO) {
         issueID = new IssueID(testDTO.issueID);
        name = new Name(testDTO.name);
         lastResult = TestResult.parse(testDTO.lastResult);
         dateLastRun = MyDateTime.parse(testDTO.dateLastRun);
         datePreviousResult = MyDateTime.parse(testDTO.datePreviousResult);
         filePath = new MyString(testDTO.filePath);
        comments = new MyString(testDTO.comments);
        runner = new Name(testDTO.runner);
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
        return testDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return issueID.equals(test.issueID) && name.equals(test.name) && lastResult == test.lastResult && runner.equals(test.runner) && dateLastRun.equals(test.dateLastRun) && datePreviousResult.equals(test.datePreviousResult) && filePath.equals(test.filePath) && comments.equals(test.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueID, name, lastResult, runner, dateLastRun, datePreviousResult, filePath, comments);
    }

    @Override
    public String toString() {
        return "Test{" + "issueID=" + issueID + ", name=" + name + ", lastResult=" + lastResult + ", runner=" + runner + ", dateLastRun=" + dateLastRun + ", datePreviousResult=" + datePreviousResult + ", filePath='" + filePath + '\'' + ", comments='" + comments + '\'' + '}';
    }


    public void updateWithTestRun(TestRun tr) {

        TestUpdatedFromTestRun(tr);
    }

    public void TestUpdatedFromTestRun(TestRun tr) {
        if (!tr.getIssueID().equals(issueID)) {
            System.err.println(" Trying to update wrong test " + issueID + " with " + tr.getIssueID());
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
}
