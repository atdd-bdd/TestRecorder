package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.DomainTerms.MyFileSystem;
import kenpugh.TestRecorder.DomainTerms.MyString;
import kenpugh.TestRecorder.Entities.MyConfiguration;
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
    private JTextArea commentsTextField;
    private JTextField runnerTextField;
    private JTextField filePathTextField;
    private JButton browseButton;
    public TestDTO testDTO;

    public TestEntryDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        issueIDTextField.setInputVerifier(new IssueIDInputVerifier());
        issueIDTextField.setVerifyInputWhenFocusTarget(true);
        filePathTextField.setInputVerifier(new NameInputVerifier());
        filePathTextField.setVerifyInputWhenFocusTarget(true);
        nameTextField.setInputVerifier(new NameInputVerifier());
        nameTextField.setVerifyInputWhenFocusTarget(true);
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
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rootFathString = MyConfiguration.rootFilePath.toString();
                JFileChooser jfc =
                        new JFileChooser(rootFathString);
                jfc.setDialogTitle("Choose the manual script file: ");
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(TestRecorderFormSwing.frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filename = String.valueOf(jfc.getSelectedFile());
                    String filename1 = filename.replace(rootFathString,"");
                    filePathTextField.setText(filename1);

                }

            }
        });
    }
    void initialize() {
        testDTO = new TestDTO();
        issueIDTextField.setText(testDTO.issueID);
        nameTextField.setText(testDTO.name);
        filePathTextField.setText(testDTO.filePath);
        commentsTextField.setText( testDTO.comments);
        dateResultTextField.setText(testDTO.dateLastRun);
        datePreviousResultTextField.setText(testDTO.datePreviousResult);
        runnerTextField.setText(testDTO.runner);
        lastResultTextField.setText( testDTO.lastResult);

    }
    private void onOK() {
          testDTO.issueID = issueIDTextField.getText();
        testDTO.name = nameTextField.getText();
        testDTO.filePath = filePathTextField.getText();
        testDTO.comments = commentsTextField.getText();
        testDTO.dateLastRun = dateResultTextField.getText();
        testDTO.datePreviousResult = datePreviousResultTextField.getText();
        testDTO.runner = runnerTextField.getText();
        testDTO.lastResult = lastResultTextField.getText();
        MyString filePathString = new MyString(testDTO.filePath);
        if (!MyFileSystem.checkReadabiity(filePathString)) {
            JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                    "File " + filePathString + " is not readable");
            testDTO = null;
        }
        dispose();
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        TestEntryDialog dialog = new TestEntryDialog();
        dialog.initialize();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
