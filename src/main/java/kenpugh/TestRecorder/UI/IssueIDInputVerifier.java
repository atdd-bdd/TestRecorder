package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.domainTerms.IssueID;
import kenpugh.TestRecorder.entities.Test;
import kenpugh.TestRecorder.entities.TestCollection;

import javax.swing.*;


    public class IssueIDInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String text = ((JTextField) input).getText();
            IssueID issueID = new IssueID(text);
            if (!issueID.equals(IssueID.INVALID_ISSUE_ID)) {
                if (!TestCollection.findTest(issueID).equals(Test.NOT_FOUND)) {
                    JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Issue ID already exists");
                    return false;
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Issue ID must have 5 digits");
                return false;
            }

        }
    }


