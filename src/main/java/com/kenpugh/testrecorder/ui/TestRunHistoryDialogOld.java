package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.entities.TestRunCollection;
import com.kenpugh.testrecorder.entities.TestRunDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class TestRunHistoryDialogOld extends JDialog {
    public List<TestRunDTO> testRunDTOs;
    public IssueID issueID;
    public SubIssueID subIssueID;
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable testRunTable;
    private DefaultTableModel tableModel;

    public TestRunHistoryDialogOld() {
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

    public static void main(String[] args) {
        TestRunHistoryDialogOld dialog = new TestRunHistoryDialogOld();
        dialog.testRunDTOs = TestRunCollection.listTestRunDTOfromListTestRun(
                TestRunCollection.findTestRuns(new IssueID("12345"), new SubIssueID("abc")));

        dialog.updateData();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void updateData() {
        tableModel.setNumRows(0);
        for (TestRunDTO testRunDTO : testRunDTOs) {
            String[] data = new String[6];
            data[0] = testRunDTO.issueID;
            data[1] = testRunDTO.subIssueID;
            data[2] = testRunDTO.runner;
            data[3] = testRunDTO.result;
            data[4] = testRunDTO.dateTime;
            data[5] = testRunDTO.comments;
            tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();

    }

    private void setUpTable() {

        String[] columnNames = {
                "Issue ID",
                "Sub Issue ID",
                "Runner",
                "Result",
                "Date Time",
                "Comments"
        };
        Vector<String> columnHeaders = new Vector<>(List.of(columnNames));
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnHeaders);
        testRunTable = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        testRunTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        testRunTable.setFillsViewportHeight(true);


    }

    private void createUIComponents() {
        setUpTable();
    }
}
