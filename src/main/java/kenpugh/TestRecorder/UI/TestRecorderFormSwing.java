package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.Database.DatabaseSetup;
import kenpugh.TestRecorder.Entities.Test;
import kenpugh.TestRecorder.Entities.TestCollection;

import javax.swing.*;
import java.util.List;

public class TestRecorderFormSwing {


    private JPanel aPanel;
    private JPanel aTable;
    private JButton addTest;
    private JButton runTest;
    private JButton showHistory;

    public static void main(String[] args) {
        getData();
        JFrame frame = new JFrame("TestRecorderFormSwing");
        TestRecorderFormSwing testRecorderFormSwing = new TestRecorderFormSwing();
        frame.setContentPane(testRecorderFormSwing.aPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    static private void getData() {
            DatabaseSetup.open();
            List<Test> tests = TestCollection.getAll();
            /*
            TestTableSwing testTableSwing = (TestTableSwing) testRecorderFormSwing.aTable;
            testTableSwing.tests = tests;
            testTableSwing.updateData();
            */
        }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.aTable = new TestTableSwing();
        /*

         */
    }
}
