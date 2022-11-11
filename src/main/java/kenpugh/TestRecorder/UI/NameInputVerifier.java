package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.DomainTerms.Name;

import javax.swing.*;


public class NameInputVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        System.err.println("**** verifying " + text);
        Name name = new Name(text);
        String empty = "";
        if (!name.equals(Name.NOT_SPECIFIED) && !name.equals(empty)) {
            System.out.println("name " + name);
            return true;
        } else
            return false;

    }
}



