package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.TestResult;
import com.kenpugh.testrecorder.entities.TestRun;
import com.kenpugh.testrecorder.entities.TestRunDTO;
import com.kenpugh.testrecorder.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestRunDialog extends JDialog {
    public TestRunDTO testRunDTO = new TestRunDTO();
    public TestRun testRun;
    public boolean result = false;
    public String scriptText = "No script";
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField runnerTextField;
    private JRadioButton successRadioButton;
    private JRadioButton failureRadioButton;
    private JTextArea commentsTextArea;
    private JTextArea scriptTextArea;
    private JTextField dateTimeTextField;
    private JTextField issueIDTextField;

    public TestRunDialog() {
        setContentPane(contentPane);
        initializeData();
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        UIHelpers.setUIFont(new javax.swing.plaf.FontUIResource(new Font("MS Mincho", Font.PLAIN, 16)));
        TestRunDialog dialog = new TestRunDialog();
        dialog.testRun = new TestRun();
        dialog.testRunDTO = dialog.testRun.getDTO();
        dialog.initializeData();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void initializeData() {
        runnerTextField.setText(testRunDTO.runner);
        commentsTextArea.setText(testRunDTO.comments);
        dateTimeTextField.setText(testRunDTO.dateTime);
        issueIDTextField.setText(testRunDTO.issueID);
        successRadioButton.setSelected(false);
        failureRadioButton.setSelected(true);
        successRadioButton.setEnabled(true);
        failureRadioButton.setEnabled(true);
        scriptTextArea.setText(scriptText);
    }

    private void onOK() {
        testRunDTO.comments = commentsTextArea.getText();
        if (successRadioButton.isSelected())
            testRunDTO.result = TestResult.Success.toString();
        else if (failureRadioButton.isSelected())
            testRunDTO.result = TestResult.Failure.toString();
        else
            Log.write(Log.Level.Severe, "Test Run test result is not set", "");
        result = true;
        dispose();
    }

    private void onCancel() {
        result = false;
        dispose();
    }

    public void enableNothing() {
        commentsTextArea.setEditable(false);
        if (testRunDTO.result.equals(TestResult.Success.toString())) {
            successRadioButton.setSelected(true);
            failureRadioButton.setSelected(false);
        } else {
            successRadioButton.setSelected(false);
            failureRadioButton.setSelected(true);
        }
        successRadioButton.setEnabled(false);
        failureRadioButton.setEnabled(false);
    }
}
