package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.Entities.Test;
import kenpugh.TestRecorder.Entities.TestCollection;

import javax.swing.*;


    public class IssueIDInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String text = ((JTextField) input).getText();
            IssueID issueID = new IssueID(text);
            if (!issueID.equals(IssueID.INVALID_ISSUE_ID)) {
                System.out.println("IssueID " + issueID);
                if (!TestCollection.findTest(issueID).equals(Test.NOT_FOUND)) {
                    System.out.println("*** Already exists ");
                    JOptionPane.showMessageDialog(null, "Issue ID already exists");
                    return false;
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Issue ID must have 5 digits");
                return false;
            }

        }
    }


