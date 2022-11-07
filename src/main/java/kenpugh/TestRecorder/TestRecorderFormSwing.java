package kenpugh.TestRecorder;

import javax.swing.*;
import java.util.List;

public class TestRecorderFormSwing {


    private JPanel aPanel;
    private JPanel aTable;
    private JButton addTest;
    private JButton runTest;
    private JButton showHistory;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TestRecorderFormSwing");
        frame.setContentPane(new TestRecorderFormSwing().aPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.aTable = new TestTableSwing();
    }
}
