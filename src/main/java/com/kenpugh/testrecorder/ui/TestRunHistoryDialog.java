package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.domainterms.MyFileSystem;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.entities.MyConfiguration;
import com.kenpugh.testrecorder.entities.Test;
import com.kenpugh.testrecorder.entities.TestCollection;
import com.kenpugh.testrecorder.entities.TestRunDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class TestRunHistoryDialog extends JDialog {
    public List<TestRunDTO> testRunDTOs;
    private JPanel contentPane;
    private JButton testRunDetailButton;
    private JButton buttonCancel;
    private JTable testRunTable;
    private DefaultTableModel tableModel;

    private final String labelForPreferences = "TestRun";

    public TestRunHistoryDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

        testRunDetailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTestRunDetail();
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

    private void showTestRunDetail() {
        int row = getTableRowIndex();
        if (row < 0) return;
        TestRunDialog dialog = new TestRunDialog();
        TestRunDTO selectedTestRunDTO = testRunDTOs.get(row);
        dialog.testRunDTO = selectedTestRunDTO;
        dialog.scriptText = getScriptText( selectedTestRunDTO);
        dialog.initializeData();
        dialog.enableNothing();
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private String getScriptText(TestRunDTO selectedTestRunDTO) {
        Test test = TestCollection.findTest(new IssueID(selectedTestRunDTO.issueID),
                new SubIssueID(selectedTestRunDTO.subIssueID));
        String scriptText = MyFileSystem.read(test.getFilePath());
        if (scriptText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "File " + test.getFilePath().toString() + " is not readable " + " root is " +
                            MyConfiguration.rootFilePath.toString());
        }
        return scriptText;
    }

    public static void main(String[] args) {
        TestRunHistoryDialog dialog = new TestRunHistoryDialog();
        // put something in to test it
        dialog.testRunDTOs = new ArrayList<>();
        dialog.testRunDTOs.add(new TestRunDTO());
        dialog.testRunDTOs.add(new TestRunDTO());
        dialog.updateData();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onCancel() {
        int [] columnWidths =  UIHelpers.getColumnWidths(testRunTable);
        UIHelpers.storeColumnWidthsIntoPreferences(columnWidths, labelForPreferences);

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
        testRunTable.setUpdateSelectionOnSort(true);
        testRunTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel.fireTableDataChanged();
        if (testRunDTOs.size() > 0)
            testRunTable.changeSelection(0,0,false, false);
        int [] columnWidths = UIHelpers.loadColumnWidthsFromPreferences(testRunTable, labelForPreferences) ;
        UIHelpers.setColumnWidths (testRunTable, columnWidths);
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
        testRunTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
        testRunTable.setFillsViewportHeight(true);

        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(tableModel);
        testRunTable.setRowSorter(tableRowSorter);
        tableRowSorter.setComparator(4, new Comparator<String>() {

            @Override
            public int compare(String name1, String name2) {
                MyDateTime date1 = new MyDateTime(name1);
                MyDateTime date2 = new MyDateTime(name2);
                return date1.compareTo(date2);
            }
        });

    }


    private void createUIComponents() {
        setUpTable();
    }

    private int getTableRowIndex() {
        int currentSelectedRow = testRunTable.getSelectedRow();
        if (currentSelectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Nothing selected ");
            return currentSelectedRow;
        }
        int row = testRunTable.convertRowIndexToModel(currentSelectedRow);
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Unable to convert selected to row ");
            return row;
        }
        int lastRow = testRunDTOs.size() - 1;
        if (row > lastRow)
            row = lastRow;
        return row;
    }

}
