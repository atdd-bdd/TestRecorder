package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.*;
import com.kenpugh.testrecorder.entities.MyConfiguration;
import com.kenpugh.testrecorder.entities.TestDTO;
import com.kenpugh.testrecorder.log.Log;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class TestEntryDialog extends JDialog {
    public boolean testValid = false;
    private JPanel contentPane2;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField issueIDTextField;
    private JTextField lastResultTextField;
    private JTextField dateResultTextField;
    private JTextField datePreviousResultTextField;
    private JTextArea commentsTextField;
    private JTextField runnerTextField;
    private JTextField filePathTextField;
    private JButton browseButton;
    private JTextField subIssueIDTextField;
    private JComboBox<String> testStatusComboBox;
    public TestDTO testDTO;

    public TestEntryDialog() {
        setContentPane(contentPane2);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        subIssueIDTextField.setInputVerifier(new SubIssueIDInputVerifier());
        subIssueIDTextField.setVerifyInputWhenFocusTarget(true);
         issueIDTextField.setInputVerifier(new IssueIDInputVerifier());
        issueIDTextField.setVerifyInputWhenFocusTarget(true);
          nameTextField.setInputVerifier(new NameInputVerifier());
        nameTextField.setVerifyInputWhenFocusTarget(true);
        testDTO = new TestDTO();

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
        contentPane2.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rootPathString = MyConfiguration.rootFilePath.toString();
                JFileChooser jfc =
                        new JFileChooser(rootPathString);
                jfc.setDialogTitle("Choose the manual script file: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(TestRecorderFormSwing.frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filename = String.valueOf(jfc.getSelectedFile());
                    String filename1 = filename.replace(rootPathString,"");
                    Log.write(Log.Level.Debug, "", "filename "  + filename + " replaced " + filename1 + " root " + MyConfiguration.rootFilePath.toString());
                    filePathTextField.setText(filename1);

                }

            }
        });
    }
    public void initialize() {
        if (!testDTO.issueID.equals(IssueID.NOT_SPECIFIED))
            issueIDTextField.setText(testDTO.issueID);

        nameTextField.setText(testDTO.name);
        filePathTextField.setText(testDTO.filePath);
        commentsTextField.setText( testDTO.comments);
        dateResultTextField.setText(testDTO.dateLastRun);
        datePreviousResultTextField.setText(testDTO.datePreviousResult);
        runnerTextField.setText(testDTO.runner);
        lastResultTextField.setText( testDTO.lastResult);
        if (!testDTO.subIssueID.equals(SubIssueID.NOT_SPECIFIED))
             subIssueIDTextField.setText(testDTO.subIssueID);
        initializeTestStatus(testDTO.testStatus);
    }

    private void initializeTestStatus(String testStatus) {
        for (TestStatus value : TestStatus.values()) {
            testStatusComboBox.addItem(value.toString());
        }
        testStatusComboBox.setSelectedItem(testStatus);
    }

    private void onOK() {
        testValid = true;
        UpdateTestDTOFromFields();
        UpdateTestStatus();
        if (!new IssueIDInputVerifier().verify(issueIDTextField))
            testValid = false;
        if (!new SubIssueIDInputVerifier().verify(subIssueIDTextField))
            testValid = false;
        if (!new NameInputVerifier().verify(nameTextField))
            testValid = false;
        CheckFilePath();
        if (testValid){
            dispose();
            }
    }

    private void CheckFilePath() {
        MyString filePathString = new MyString(testDTO.filePath);
        if (!MyFileSystem.checkReadability(filePathString)) {
            JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                    "File " + filePathString + " is not readable");
            testValid = false;
        }
    }

    private void UpdateTestStatus() {
        String temp = Objects.requireNonNull(testStatusComboBox.getSelectedItem()).toString();
        if (temp == null)
            temp = "Active";
        testDTO.testStatus = temp;
    }

    private void UpdateTestDTOFromFields() {
        testDTO.issueID = issueIDTextField.getText();
        testDTO.name = nameTextField.getText();
        testDTO.filePath = filePathTextField.getText();
        testDTO.comments = commentsTextField.getText();
        testDTO.dateLastRun = dateResultTextField.getText();
        testDTO.datePreviousResult = datePreviousResultTextField.getText();
        testDTO.runner = runnerTextField.getText();
        testDTO.lastResult = lastResultTextField.getText();
        testDTO.subIssueID = subIssueIDTextField.getText();
    }


    private void onCancel() {
        // add your code here if necessary
        testValid = false;
        dispose();
    }

    public static void main(String[] args) {
        TestEntryDialog dialog = new TestEntryDialog();
        dialog.initialize();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void enableStatusOnly() {
        nameTextField.setEditable(false);
        issueIDTextField.setEditable(false);
        subIssueIDTextField.setEditable(false);
        lastResultTextField.setEditable(false);
        filePathTextField.setEditable(false);
        browseButton.setEnabled(false);
    }
}
