package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Test {
    private IssueID issueID = IssueID.INVALID_ISSUE_ID;
   private Name name = new Name(Name.NOT_SPECIFIED);
    private TestResult lastResult = TestResult.Failure;
    private Name runner = new Name("");
    private MyDateTime dateLastRun = MyDateTime.NEVER_DATETIME;
    private MyDateTime datePreviousResult = MyDateTime.NEVER_DATETIME;
    private MyString filePath = new MyString("File Path Not Specified");
    private MyString comments = new MyString("No comment");
    public final static Test NOT_FOUND = new Test();
    public boolean selectiveEquals(Object o, TestForEquals testForEquals) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;

        boolean result = (issueID.equals(test.issueID) || !testForEquals.issueIDCheck)
                && (name.equals(test.name) || !testForEquals.nameCheck)
                && (lastResult == test.lastResult || !testForEquals.lastResultCheck)
                && (runner.equals(test.runner)  || !testForEquals.runnerCheck)
                && (dateLastRun.equals(test.dateLastRun)  || !testForEquals.dateLastRunCheck)
                && (datePreviousResult.equals(test.datePreviousResult) || !testForEquals.datePreviousResultCheck)
                && (filePath.equals(test.filePath) || !testForEquals.filePathCheck)
                && (comments.equals(test.comments) || !testForEquals.commentsCheck);
    if (!result)
            System.out.println("Selective equal values " + " for " + this + " == " + test + " selections: "
                    + testForEquals );
    return result;
    }
    public void fromDTO(@NotNull TestDTO testDTO) {
        if (testDTO.issueID != null) issueID = new IssueID(testDTO.issueID);
        if (testDTO.name != null) name = new Name(testDTO.name);
        if (testDTO.lastResult != null) lastResult = TestResult.parse(testDTO.lastResult);
        if (testDTO.dateLastRun != null) dateLastRun = MyDateTime.parse(testDTO.dateLastRun);
        if (testDTO.datePreviousResult != null) datePreviousResult = MyDateTime.parse(testDTO.datePreviousResult);
        if (testDTO.filePath != null) filePath = new MyString(testDTO.filePath);
        if (testDTO.comments != null) comments = new MyString(testDTO.comments);
        if (testDTO.runner != null) runner = new Name(testDTO.runner);
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
