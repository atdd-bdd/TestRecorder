package kenpugh.TestRecorder.UI;


import kenpugh.TestRecorder.Entities.Test;
import kenpugh.TestRecorder.Entities.TestCollection;
import kenpugh.TestRecorder.Entities.TestRun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class TestRecorderFormSwing {


    private JPanel aPanel;
    private JPanel aTable;
    private JButton addTest;
    private JButton runTest;
    private JButton showHistory;
    static TestRecorderFormSwing testRecorderFormSwing;

    public TestRecorderFormSwing() {
        runTest.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                TestRunDialog dialog = new TestRunDialog();
                dialog.pack();
                dialog.setVisible(true);
                if (dialog.testRun != null) {
                    dialog.testRun = new TestRun();  // need to pick this up from Test
                    dialog.testRunDTO = dialog.testRun.getDTO();
                    System.out.println(dialog.testRun);
                }
                else
                    System.out.println("Dialog cancelled");

            }
        });
        addTest.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        showHistory.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
            {
                UIManager.put(key, f);
            }
        }
    }

    public static void main(String[] args) {

        setUIFont (new javax.swing.plaf.FontUIResource(new Font("MS Mincho",Font.PLAIN, 16)));
        JFrame frame = new JFrame("TestRecorderFormSwing");
        testRecorderFormSwing = new TestRecorderFormSwing();
        frame.setContentPane(testRecorderFormSwing.aPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        getData();
    }

    static private void getData() {
            List<Test> tests = TestCollection.getAll();
            TestTableSwing testTableSwing = (TestTableSwing) testRecorderFormSwing.aTable;
            testTableSwing.tests = tests;
            testTableSwing.updateData();
        }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.aTable = new TestTableSwing();
        /*

         */
    }
}
