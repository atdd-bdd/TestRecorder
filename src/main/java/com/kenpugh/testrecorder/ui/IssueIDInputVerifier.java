package com.kenpugh.testrecorder.ui;



import com.kenpugh.testrecorder.domainterms.IssueID;


import javax.swing.*;


    public class IssueIDInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String text = ((JTextField) input).getText();
            IssueID issueID = new IssueID(text);
            if (!issueID.equals(IssueID.INVALID_ISSUE_ID))
                return true;
            else {
                JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Issue ID must have 5 characters");
                return false;
            }

        }
    }


