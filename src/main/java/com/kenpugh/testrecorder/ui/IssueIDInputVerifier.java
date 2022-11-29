package com.kenpugh.testrecorder.ui;


import com.kenpugh.testrecorder.domainterms.IssueID;

import javax.swing.*;


public class IssueIDInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();

        if (IssueID.CheckValue(text))
            return true;
        else {
            JOptionPane.showMessageDialog(input, "Issue ID must have 5 characters");
            return false;
        }

    }
}


