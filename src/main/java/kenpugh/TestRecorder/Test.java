package kenpugh.TestRecorder;

import java.util.Objects;

class Test {
    IssueID issueID = IssueID.INVALID_ISSUE_ID;
    Name name = new Name();
    TestResult lastResult = TestResult.Failure;
    Name runner = new Name();
    MyDateTime dateLastRun  = new MyDateTime();
    MyDateTime datePreviousResult = new MyDateTime();
    MyString filePath = new MyString("File Path Not Specified");
    MyString comments = new MyString("No comment");
    static Test NOT_FOUND = new Test();

      public void fromDTO(TestDTO testDTO) {
        if (testDTO.issueID != null) issueID = new IssueID(testDTO.issueID);
        if (testDTO.name != null) name = new Name(testDTO.name);
        if (testDTO.lastResult != null) lastResult = TestResult.valueOf(testDTO.lastResult);
        if (testDTO.dateLastRun != null) dateLastRun = MyDateTime.parse(testDTO.dateLastRun);
        if (testDTO.datePreviousResult != null) datePreviousResult = MyDateTime.parse(testDTO.datePreviousResult);
        if (testDTO.filePath != null) filePath = new MyString(testDTO.filePath);
        if (testDTO.comments != null) comments = new MyString(testDTO.comments);
        if (testDTO.runner != null) runner = new Name(testDTO.runner);
    }
    static public Test testFromDTO(TestDTO testDTO){
          Test result = new Test();
          result.fromDTO((testDTO));
          return result;
    }
    TestDTO getDTO() {
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
          System.out.println(" Updating test " + this);
          System.out.println(" With test run " + tr);
        if (!tr.issueID.equals(issueID)) {
            System.err.println(" Trying to update wrong test " + issueID + " with " + tr.issueID);
            return;
        }

        runner = tr.runner;
        comments = tr.comments;
        if (tr.testResult != lastResult) {
            datePreviousResult = dateLastRun;
        }
        dateLastRun = tr.dateTime;
        lastResult = tr.testResult;
        System.out.println(" Test is now " + this);
    }
}
