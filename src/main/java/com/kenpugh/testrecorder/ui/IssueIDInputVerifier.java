package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.entities.Test;
import com.kenpugh.testrecorder.entities.TestCollection;

import javax.swing.*;


    public class IssueIDInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            /*
            String text = ((JTextField) input).getText();
            IssueID issueID = new IssueID(text);
            SubIssueID subIssueID = new SubIssueID()
            if (!issueID.equals(IssueID.INVALID_ISSUE_ID)) {
                if (!TestCollection.findTest(issueID).equals(Test.NOT_FOUND)) {
                    JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Issue ID already exists");
                    return false;
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Issue ID must have 3 characters");
                return false;
            }
*/
            return true;
        }
    }

