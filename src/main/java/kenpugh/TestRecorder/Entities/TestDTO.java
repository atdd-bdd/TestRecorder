package kenpugh.TestRecorder.Entities;
// | Issue ID  | Name  | Last Result  | Runner | Date Last Run  | Date Previous Result  | File Path |

import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.DomainTerms.TestResult;

public class TestDTO {
    public String issueID  = IssueID.NOT_SPECIFIED;
    public String  name = "NO NAME";
    public String  lastResult = TestResult.Failure.toString();
    public String runner = "NO NAME";
    public String dateLastRun = MyDateTime.NEVER;
    public String datePreviousResult = MyDateTime.NEVER;
    public String filePath = "NO PATH";
    public String comments = "NO COMMENTs";
    public static TestDTO NOT_FOUND = new TestDTO();
    }
