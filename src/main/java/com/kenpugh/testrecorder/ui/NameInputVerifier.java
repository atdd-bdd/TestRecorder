package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.Name;

import javax.swing.*;


public class NameInputVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        Name name = new Name(text);
        String empty = "";
        String nameString = name.toString();
        ((JTextField) input).setText(nameString);
        return !nameString.equals(Name.NOT_SPECIFIED) && !nameString.equals(empty);

    }
}



