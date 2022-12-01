package com.kenpugh.testrecorder.ui;

import java.io.*;
import java.lang.Thread;

import com.kenpugh.testrecorder.domainterms.*;
import com.kenpugh.testrecorder.entities.*;
import com.kenpugh.testrecorder.log.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


public class TestRecorder {
    private static final String labelForPreferences = "Test";
    static public boolean inProgress = false;
    public static TestRecorder testRecorderFormSwing;
    public static JFrame frame;
    public List<TestDTO> testDTOs;
    public TestFilter testFilter = new TestFilter();
    private JPanel aPanel;
    private JButton addTestButton;
    private JButton runTestButton;
    private JButton showHistoryButton;
    private JTable testTable;
    private JButton filterTestsButton;
    private JButton changeStatusButton;
    private DefaultTableModel tableModel;
    private TestDTO currentTestDTO = new TestDTO();
    private int currentSelectedRow = 0;

    public TestRecorder() {
        runTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTest();
            }
        });
        addTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTest();
            }
        });
        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTestRunHistory();
            }
        });

        filterTestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTests();
            }
        });
        changeStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeStatus();
            }
        });
    }


    private static void setWindowListener() {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Log.write(Log.Level.Info, "Window is closing", "");
                TestRecorder.inProgress = false;
            }
        });

    }

    private void changeStatus() {
        TestEntryDialog dialog = new TestEntryDialog();
        int row = getTableRowIndexAndSetSelectedRowDTO();
        if (row < 0) return;
        dialog.testDTO = testDTOs.get(row);
        dialog.initialize();
        dialog.enableStatusOnly();
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        if (dialog.testValid) {
            Test test = Test.testFromDTO(dialog.testDTO);
            if (!TestCollection.updateTest(test)) {
                JOptionPane.showMessageDialog(frame,
                        "Test for Issue ID " + dialog.testDTO.issueID +
                                " SubIssue ID " + dialog.testDTO.subIssueID + " could not update ");

            }
            testRecorderFormSwing.updateData();
        }
    }

    private void filterTests() {
        getTableRowIndexAndSetSelectedRowDTO();
        TestFilterDialog dialog = new TestFilterDialog();
        dialog.testFilter = testFilter;
        dialog.initialize();
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        testFilter = dialog.testFilter;
        updateData();
    }

    private void showTestRunHistory() {
        TestRunHistoryDialog dialog = new TestRunHistoryDialog();
        int row = getTableRowIndexAndSetSelectedRowDTO();
        if (row < 0) return;
        TestDTO testDTO = testDTOs.get(row);

        dialog.testRunDTOs = TestRunCollection.listTestRunDTOfromListTestRun(
                TestRunCollection.findTestRuns(new IssueID(testDTO.issueID),
                        new SubIssueID(testDTO.subIssueID)));
        dialog.updateData();
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void addTest() {
        getTableRowIndexAndSetSelectedRowDTO();
        TestEntryDialog dialog = new TestEntryDialog();
        dialog.initialize();
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        if (dialog.testValid) {
            Test test = Test.testFromDTO(dialog.testDTO);
            if (!TestCollection.addTest(test)) {
                JOptionPane.showMessageDialog(frame,
                        "Test for Issue ID " + dialog.testDTO.issueID +
                                " SubIssue ID " + dialog.testDTO.subIssueID + " already exists ");

            }
            currentTestDTO = dialog.testDTO;
            testRecorderFormSwing.updateData();

        }
    }

    private void runTest() {
        int row = getTableRowIndexAndSetSelectedRowDTO();
        if (row < 0) return;

        TestDTO selectedTestDTO = testDTOs.get(row);
        TestRun testRun = TestRun.getBaseTestRun(
                new IssueID(selectedTestDTO.issueID),
                new SubIssueID(selectedTestDTO.subIssueID));
        TestRunDialog dialog = new TestRunDialog();
        dialog.testRunDTO = testRun.getDTO();
        dialog.scriptText = MyFileSystem.read(new MyString(selectedTestDTO.filePath));
        if (dialog.scriptText.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "File " + selectedTestDTO.filePath + " is not readable " + " root is " +
                            MyConfiguration.rootFilePath.toString());
            return;
        }
        dialog.initializeData();
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        if (dialog.result) {
            testRun = TestRun.TestRunFromDTO(dialog.testRunDTO);
            TestRunandTestCollaborator.findTestAndUpdateWithTestRun(testRun.getIssueID(), testRun.getSubIssueID(), testRun);
            testRecorderFormSwing.updateData();
        }
    }

    private int getTableRowIndexAndSetSelectedRowDTO() {
        if (testDTOs.size() == 0){
            currentSelectedRow = -1;
            return currentSelectedRow;
        }
        currentSelectedRow = testTable.getSelectedRow();
        if (currentSelectedRow < 0) {
            JOptionPane.showMessageDialog(frame,
                    "Nothing selected ");
            return currentSelectedRow;
        }
        int row = testTable.convertRowIndexToModel(currentSelectedRow);
        if (row < 0) {
            JOptionPane.showMessageDialog(frame,
                    "Unable to convert selected to row ");
            return row;
        }
        int lastRow = testDTOs.size() - 1;
        if (row > lastRow)
            row = lastRow;

        storeCurrentColumnWidths();

        currentTestDTO = testDTOs.get(row);

        return row;
    }

    private void storeCurrentColumnWidths() {
        int[] columnWidths = UIHelpers.getColumnWidths(testTable);
        UIHelpers.storeColumnWidthsIntoPreferences(columnWidths, labelForPreferences);
    }
    private void createUIComponents() {
        setUpTable();
    }

    private void setUpTable() {
        String[] columnNames = new String[]{
                "Issue ID",
                "Sub Issue ID",
                "Name",
                "Runner",
                "Last Result",
                "Date Last Run",
                "Date Previous Result",
                "File Path ",
                "Comments",
                "Status"
        };
        Vector<String> columnHeaders = new Vector<>(List.of(columnNames));
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnHeaders);
        testTable = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        testTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        testTable.setFillsViewportHeight(true);
        testTable.setRowSelectionAllowed(true);

        setColumnWidthsFromPreferences();

    }

    private void setColumnWidthsFromPreferences() {
        int[] columnWidths = UIHelpers.loadColumnWidthsFromPreferences(testTable, labelForPreferences);
        UIHelpers.setColumnWidths(testTable, columnWidths);
    }

    public void updateData() {
        List<Test> allTests = TestCollection.getAll();
        List<Test> tests = TestCollection.filter(allTests, testFilter);

        testDTOs = TestCollection.listTestDTOFromListTest(tests);
        tableModel.setNumRows(0);
        for (TestDTO testDTO : testDTOs) {
            String[] data = new String[10];
            testDTO.dateLastRun = MyDateTime.toDisplayString(testDTO.dateLastRun);
            testDTO.datePreviousResult = MyDateTime.toDisplayString(testDTO.datePreviousResult);
            data[0] = testDTO.issueID;
            data[1] = testDTO.subIssueID;
            data[2] = testDTO.name;
            data[3] = testDTO.runner;
            data[4] = testDTO.lastResult;
            data[5] = testDTO.dateLastRun;
            data[6] = testDTO.datePreviousResult;
            data[7] = testDTO.filePath;
            data[8] = testDTO.comments;
            data[9] = testDTO.testStatus;
            tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();

        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(tableModel);
        testTable.setRowSorter(tableRowSorter);
        tableRowSorter.setComparator(5, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                MyDateTime date1 = new MyDateTime(name1);
                MyDateTime date2 = new MyDateTime(name2);
                return date1.compareTo(date2);
            }
        });
        tableRowSorter.setComparator(6, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                MyDateTime date1 = new MyDateTime(name1);
                MyDateTime date2 = new MyDateTime(name2);
                return date1.compareTo(date2);
            }
        });

        tableModel.fireTableDataChanged();

        setCurrentRow();
    }

    private void setCurrentRow() {
        if (currentSelectedRow >= 0) {
            for (int i = 0; i < testDTOs.size(); i++) {
                int rowDTO = testTable.convertRowIndexToModel(i);
                TestDTO entry = testDTOs.get(rowDTO);
                if (entry.issueID.equals(currentTestDTO.issueID) &&
                        entry.subIssueID.equals(currentTestDTO.subIssueID)) {
                    currentSelectedRow = i;
                    break;
                }
            }
        }
        if (currentSelectedRow >= 0)
            testTable.changeSelection(currentSelectedRow, 0, false, false);
        else if (testDTOs.size() > 0) {
            testTable.changeSelection(currentSelectedRow, 0, false, false);
            currentSelectedRow = 0;
        }
        else
            currentSelectedRow = -1;
    }

    public static void main(String[] args) {
        MyConfiguration.loadFromFile();
        try {
            UIManager.createLookAndFeel("Windows");
        } catch (UnsupportedLookAndFeelException e) {
            Log.write(Log.Level.Severe, "Look and feel not available", "Windows");
        }
        UIHelpers.setUIFont(new javax.swing.plaf.FontUIResource(new Font("MS Mincho", Font.PLAIN, 16)));
        frame = new JFrame("Test Recorder");
        setIconImageFromFile("icon.gif");
        TestRecorder.inProgress = true;

        testRecorderFormSwing = new TestRecorder();
        testRecorderFormSwing.testFilter.includeActive = true;
        frame.setContentPane(testRecorderFormSwing.aPanel);
        if (!MyConfiguration.formNotCloseOnExit) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        frame.pack();
        frame.setVisible(true);
        testRecorderFormSwing.updateData();
        setWindowListener();
    }

    @SuppressWarnings("SameParameterValue")
    private static void setIconImageFromFile(String filename) {
        // Image anImage = Toolkit.getDefaultToolkit().getImage(which one?)
        // frame.setIconImage(anImage)

        InputStream isr =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);

        byte[] array;
        try {
            array = Objects.requireNonNull(isr).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageIcon ii = new ImageIcon(array);
        frame.setIconImage(ii.getImage());
    }
}
