package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.Entities.TestDTO;

import javax.swing.*;
import java.awt.event.*;

public class TestEntryDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField issueIDTextField;
    private JTextField lastResultTextField;
    private JTextField dateResultTextField;
    private JTextField datePreviousResultTextField;
    private JTextField commentsTextField;
    private JLabel IssueIDLabel;
    private JTextField runnerTextField;
    private JTextField filePathTextField;
    public TestDTO testDTO;

    public TestEntryDialog() {
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

    private void onOK() {
        testDTO = new TestDTO();
        testDTO.issueID = issueIDTextField.getText();
        testDTO.name = nameTextField.getText();
        testDTO.filePath = filePathTextField.getText();
        testDTO.comments = commentsTextField.getText();
        testDTO.dateLastRun = dateResultTextField.getText();
        testDTO.datePreviousResult = datePreviousResultTextField.getText();
        testDTO.runner = runnerTextField.getText();
        testDTO.lastResult = lastResultTextField.getText();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        TestEntryDialog dialog = new TestEntryDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
