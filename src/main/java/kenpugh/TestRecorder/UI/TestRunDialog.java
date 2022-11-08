package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.Entities.TestRun;
import kenpugh.TestRecorder.Entities.TestRunDTO;
import kenpugh.TestRecorder.DomainTerms.TestResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestRunDialog extends JDialog {
    TestRunDTO testRunDTO;
    TestRun testRun;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel valuesPanel;
    private JTextField runnerTextField;
    private JRadioButton successRadioButton;
    private JRadioButton failureRadioButton;
    private JTextArea commentsTextArea;
    private JTextArea scriptTextArea;

    public TestRunDialog() {
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
        // add your code here
        // Update testDTO
        testRunDTO.comments = commentsTextArea.getText();
        testRunDTO.runner = runnerTextField.getText();
        if (successRadioButton.isSelected())
            testRunDTO.testResult = TestResult.Success.toString();
        else if (failureRadioButton.isSelected())
            testRunDTO.testResult = TestResult.Failure.toString();
        else
            System.err.println("Test Run test result is not set");
        testRun.fromDTO(testRunDTO);
        System.out.println(testRun);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
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
        TestRunDialog dialog = new TestRunDialog();
       dialog.testRun = new TestRun();
        dialog.testRunDTO = dialog.testRun.getDTO();

        System.out.println(dialog.testRun);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
