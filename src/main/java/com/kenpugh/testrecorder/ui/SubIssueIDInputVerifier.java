package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.SubIssueID;

import javax.swing.*;

public class SubIssueIDInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (SubIssueID.CheckValue(text))
            return true;
        else {
            JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Sub Issue ID must have 3 characters");
            return false;
        }
    }
}


