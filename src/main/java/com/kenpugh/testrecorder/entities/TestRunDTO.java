package com.kenpugh.testrecorder.entities;

import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.domainterms.TestResult;

public class TestRunDTO {

    public String issueID = IssueID.NOT_SPECIFIED;
    public String dateTime = MyDateTime.NEVER_STRING;
    public String result = TestResult.Failure.toString();
    public String comments = "";
    public String runner = "";

    public String subIssueID = SubIssueID.NOT_SPECIFIED;
}
