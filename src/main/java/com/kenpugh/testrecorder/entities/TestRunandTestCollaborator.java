package com.kenpugh.testrecorder.entities;

import com.kenpugh.testrecorder.database.TestDataAccess;
import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.log.Log;

public class TestRunandTestCollaborator {
    public static void findTestAndUpdateWithTestRun(IssueID issueID,
                                                    SubIssueID subIssueID, TestRun tr) {
        Test t = TestCollection.findTest(issueID, subIssueID);
        if (t != Test.NOT_FOUND) {
            t.UpdateTestFromTestRun(tr);
            TestDTO testDTO = t.getDTO();
            if (!TestDataAccess.update(testDTO))
                Log.write(Log.Level.Severe, "Error in updating test", t.toString());
        } else
            Log.write(Log.Level.Severe, " Cannot update test - not found ", tr.getIssueID().toString());
        TestRunCollection.addTestRun(tr);
    }
}
