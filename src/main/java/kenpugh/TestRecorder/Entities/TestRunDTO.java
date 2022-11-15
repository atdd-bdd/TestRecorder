package kenpugh.TestRecorder.Entities;

import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyDateTime;
import kenpugh.TestRecorder.DomainTerms.TestResult;

public class TestRunDTO {

    public String issueID = IssueID.NOT_SPECIFIED;
    public String dateTime = MyDateTime.NEVER_STRING;
    public String result = TestResult.Failure.toString();
    public String comments = "";
    public String runner = "";

}
