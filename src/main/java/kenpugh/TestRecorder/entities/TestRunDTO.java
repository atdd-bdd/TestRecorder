package kenpugh.TestRecorder.entities;

import kenpugh.TestRecorder.domainTerms.IssueID;
import kenpugh.TestRecorder.domainTerms.MyDateTime;
import kenpugh.TestRecorder.domainTerms.TestResult;

public class TestRunDTO {

    public String issueID = IssueID.NOT_SPECIFIED;
    public String dateTime = MyDateTime.NEVER_STRING;
    public String result = TestResult.Failure.toString();
    public String comments = "";
    public String runner = "";

}
