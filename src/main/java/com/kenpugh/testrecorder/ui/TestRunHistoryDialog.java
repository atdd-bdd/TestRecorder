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

import static com.kenpugh.testrecorder.ui.TestRecorderFormSwing.frame;

public class TestRunHistoryDialog extends JDialog {
    public List<TestRunDTO> testRunDTOs;
    private JPanel contentPane;
    private JButton aestRunDetailButton;
    private JButton buttonCancel;
    private JTable testRunTable;
    private DefaultTableModel tableModel;

    public TestRunHistoryDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(aestRunDetailButton);

        aestRunDetailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = getTableRowIndex();
                if (row < 0) return;
                TestRunDialog dialog = new TestRunDialog();
                TestRunDTO selectedTestRunDTO = testRunDTOs.get(row);
                dialog.testRunDTO = selectedTestRunDTO;
                Test test = TestCollection.findTest(new IssueID(selectedTestRunDTO.issueID),
                        new SubIssueID(selectedTestRunDTO.subIssueID));
                dialog.scriptText = MyFileSystem.read(test.getFilePath());
                if (dialog.scriptText.isEmpty()) {
                    JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                            "File " + test.getFilePath().toString() + " is not readable " + " root is " +
                                    MyConfiguration.rootFilePath.toString());

                    return;
                }

                dialog.initializeData();
                dialog.enableNothing();
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);


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
        TestRunHistoryDialog dialog = new TestRunHistoryDialog();
        dialog.testRunDTOs = new ArrayList<>();
        dialog.testRunDTOs.add(new TestRunDTO());
        dialog.updateData();

        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
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
        int selected = testRunTable.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(frame,
                    "Nothing selected ");
            return selected;
        }
        int row = testRunTable.convertRowIndexToModel(selected);
        if (row < 0) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to convert selected to row ");
            return row;
        }
        int lastRow = testRunDTOs.size() - 1;
        if (row > lastRow)
            row = lastRow;
        return row;
    }

}
