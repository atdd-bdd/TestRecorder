package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.domainTerms.IssueID;
import kenpugh.TestRecorder.entities.TestRun;
import kenpugh.TestRecorder.entities.TestRunCollection;
import kenpugh.TestRecorder.entities.TestRunDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class TestRunHistoryDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable testRunTable;
    private DefaultTableModel tableModel;

    List<TestRunDTO> testRunDTOs;

    IssueID issueID;

    public TestRunHistoryDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        TestRunHistoryDialog dialog = new TestRunHistoryDialog();
        dialog.issueID = new IssueID("12345");
        dialog.updateData();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void updateData() {
        List<TestRun> testRuns = TestRunCollection.findTestRuns(issueID);
        testRunDTOs = TestRunCollection.listTestRunDTOfromListTestRun(testRuns);
        tableModel.setNumRows(0);
        for (TestRunDTO testRunDTO : testRunDTOs) {
            String[] data = new String[8];
            data[0] = testRunDTO.issueID;
            data[1] = testRunDTO.runner;
            data[2] = testRunDTO.result;
            data[3] = testRunDTO.dateTime;
            data[4] = testRunDTO.comments;
            tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();

    }

    private void setUpTable() {

        String[] columnNames = {
                "Issue ID",
                "Runner",
                "Result",
                "Date Time",
                "Comments"
        };
        Vector<String> columnHeaders = new Vector<>(List.of(columnNames));
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnHeaders);
        testRunTable = new JTable(tableModel);
        testRunTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        testRunTable.setFillsViewportHeight(true);


    }

    private void createUIComponents() {
        setUpTable();
    }
}
