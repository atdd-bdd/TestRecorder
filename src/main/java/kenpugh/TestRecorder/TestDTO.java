package kenpugh.TestRecorder;
// | Issue ID  | Name  | Last Result  | Runner | Date Last Run  | Date Previous Result  | File Path |

public class TestDTO {
    String issueID  = IssueID.NOT_SPECIFIED;
    String  name = "NO NAME";
    String  lastResult = TestResult.Failure.toString();
    String runner = "NO NAME";
    String dateLastRun = MyDateTime.NEVER;
    String datePreviousResult = MyDateTime.NEVER;
    String filePath = "NO PATH";
    String comments = "NO COMMENTs";
    static TestDTO NOT_FOUND = new TestDTO();
    }
