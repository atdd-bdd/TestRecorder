package kenpugh.TestRecorder.Entities;
// | Issue ID  | Name  | Last Result  | Runner | Date Last Run  | Date Previous Result  | File Path |

import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.DomainTerms.Name;
import kenpugh.TestRecorder.DomainTerms.TestResult;

public class TestDTO {
    public String issueID = IssueID.NOT_SPECIFIED;
    public String name = Name.EMPTY;
    public String lastResult = TestResult.Failure.toString();
    public String runner = Name.EMPTY;
    public String dateLastRun = MyDateTime.NEVER_STRING;
    public String datePreviousResult = MyDateTime.NEVER_STRING;
    public String filePath = "File Path Not Specified";
    public String comments = "";
    public final static TestDTO NOT_FOUND = new TestDTO();
}
