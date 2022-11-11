package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.DomainTerms.Name;

import javax.swing.*;


public class NameInputVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        Name name = new Name(text);
        String empty = "";
        String nameString = name.toString();
        if (!nameString.equals(Name.NOT_SPECIFIED) && !nameString.equals(empty)) {
            return true;
        } else
            return false;

    }
}



