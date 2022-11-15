package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.DomainTerms.TestResult;
import kenpugh.TestRecorder.Entities.TestRun;
import kenpugh.TestRecorder.Entities.TestRunDTO;
import kenpugh.TestRecorder.Log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestRunDialog extends JDialog {
    TestRunDTO testRunDTO = new TestRunDTO();
    TestRun testRun;
    boolean added = false;
    String scriptText = "No script";
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
        initializeData();
        setContentPane(contentPane);
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

    public void initializeData() {
        runnerTextField.setText(testRunDTO.runner);
        commentsTextArea.setText(testRunDTO.comments);
        dateTimeTextField.setText(testRunDTO.dateTime);
        issueIDTextField.setText(testRunDTO.issueID);
        successRadioButton.setSelected(false);
        failureRadioButton.setSelected(true);

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
        added = true;
        dispose();
    }

    private void onCancel() {
        added = false;
        dispose();
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void main(String[] args) {
        setUIFont(new javax.swing.plaf.FontUIResource(new Font("MS Mincho", Font.PLAIN, 16)));
        TestRunDialog dialog = new TestRunDialog();
        dialog.testRun = new TestRun();
        dialog.testRunDTO = dialog.testRun.getDTO();

        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
