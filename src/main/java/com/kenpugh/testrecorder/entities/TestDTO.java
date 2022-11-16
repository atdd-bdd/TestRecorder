package com.kenpugh.testrecorder.entities;
// | Issue ID  | Name  | Last Result  | Runner | Date Last Run  | Date Previous Result  | File Path |

import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.domainterms.Name;
import com.kenpugh.testrecorder.domainterms.TestResult;

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
