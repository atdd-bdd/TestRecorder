package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.Name;

import javax.swing.*;


public class NameInputVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        Name name = new Name(text);
        String nameString = name.toString();
        if (name.toString().length() < 1) {
            JOptionPane.showMessageDialog(TestRecorderFormSwing.frame, "Name must have at least 1 character");
            return false;
        }
        ((JTextField) input).setText(nameString);
        return true;
    }
}



