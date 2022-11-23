package com.kenpugh.testrecorder.ui;


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
import java.util.Vector;



public class TestRecorderFormSwing {
    static public boolean inProgress = false;

    private JPanel aPanel;

    private JButton addTest;
    private JButton runTestButton;
    private JButton showHistory;
    private JTable testTable;
    private JButton filterTestsButton;
    private JButton changeStatusButton;
    private DefaultTableModel tableModel;
    public static TestRecorderFormSwing testRecorderFormSwing;

    public List<TestDTO> testDTOs;

    public String selectedIssueIDString ="";   // Which row should be marked.
    public String selectedSubIssueIDString = "";
    public TestFilter testFilter = new TestFilter();
    public TestRecorderFormSwing() {
        runTestButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = getTableRowIndex();
                if (row <0)  return;

                TestDTO selectedTestDTO = testDTOs.get(row);
                TestRun testRun = TestRun.getBaseTestRun(
                        new IssueID(selectedTestDTO.issueID),
                        new SubIssueID(selectedTestDTO.subIssueID));
                TestRunDialog dialog = new TestRunDialog();
                dialog.testRunDTO = testRun.getDTO();
                dialog.scriptText = MyFileSystem.read(new MyString(selectedTestDTO.filePath));
                if (dialog.scriptText.isEmpty())
                {
                    JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                            "File " +  selectedTestDTO.filePath + " is not readable " + " root is " +
                            MyConfiguration.rootFilePath.toString());

                    return;
                }
                dialog.initializeData();
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
                if (dialog.added) {
                    testRun = TestRun.TestRunFromDTO(dialog.testRunDTO);
                    TestRunandTestCollaborator.findTestAndUpdateWithTestRun(testRun.getIssueID(), testRun.getSubIssueID(), testRun);
                    testRecorderFormSwing.updateData();
                }

            }
        });
        addTest.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                TestEntryDialog dialog = new TestEntryDialog();
                dialog.initialize();
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
                if (dialog.testValid){
                    Test test = Test.testFromDTO(dialog.testDTO);
                    if (!TestCollection.addTest(test)) {
                        JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                                "Test for Issue ID " + dialog.testDTO.issueID +
                                        " SubIssue ID " + dialog.testDTO.subIssueID + " already exists " );

                    }
                    testRecorderFormSwing.updateData();
                    selectedIssueIDString = dialog.testDTO.issueID;
                    selectedSubIssueIDString = dialog.testDTO.subIssueID;
                }

            }
        });
        showHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestRunHistoryDialog dialog = new TestRunHistoryDialog();
                int row = getTableRowIndex();
                if (row < 0) return;
                TestDTO testDTO = testDTOs.get(row);
                dialog.issueID = new IssueID(testDTO.issueID);
                dialog.subIssueID = new SubIssueID(testDTO.subIssueID);
                dialog.updateData();
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });

        filterTestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestFilterDialog dialog = new TestFilterDialog();
                dialog.testFilter = testFilter;
                dialog.initialize();
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
                testFilter = dialog.testFilter;
                updateData();
            }
        });
        changeStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestEntryDialog dialog = new TestEntryDialog();
                int row = getTableRowIndex();
                if (row <0)  return;
                dialog.testDTO = testDTOs.get(row);
                dialog.initialize();
                dialog.enableStatusOnly();
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
                if (dialog.testValid){
                    Test test = Test.testFromDTO(dialog.testDTO);
                    if (!TestCollection.updateTest(test)) {
                        JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                                "Test for Issue ID " + dialog.testDTO.issueID +
                                        " SubIssue ID " + dialog.testDTO.subIssueID + " could not update " );

                    }
                    testRecorderFormSwing.updateData();
                    selectedIssueIDString = dialog.testDTO.issueID;
                    selectedSubIssueIDString = dialog.testDTO.subIssueID;
                }


            }
        });
    }


    private int getTableRowIndex() {
        int row =  testTable.getSelectedRow();
        if (row < 0 )  {
            JOptionPane.showMessageDialog(TestRecorderFormSwing.frame,
                    "No tests  ");
            return row;
        }
        int lastRow = testDTOs.size() -1;
        if (row > lastRow)
            row = lastRow;
        return row;
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
public static JFrame frame;
    public static void main(String[] args) {
        MyConfiguration.loadFromFile();
        setUIFont(new javax.swing.plaf.FontUIResource(new Font("MS Mincho", Font.PLAIN, 16)));
        frame = new JFrame("TestRecorderFormSwing");
        TestRecorderFormSwing.inProgress = true;

        testRecorderFormSwing = new TestRecorderFormSwing();
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

    private static void setWindowListener() {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Log.write(Log.Level.Info, "Window is closing", "");
                TestRecorderFormSwing.inProgress = false;
            }
        });

}


    private void setUpTable() {
        //  super(new GridLayout(1,0));
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
            }};
        testTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        testTable.setFillsViewportHeight(true);
        testTable.setRowSelectionAllowed(true);

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

        if (testDTOs.size() >= 1) {
            if (selectedIssueIDString.isEmpty())
                selectedIssueIDString = testDTOs.get(0).issueID;
            if (selectedSubIssueIDString.isEmpty())
                selectedSubIssueIDString = testDTOs.get(0).subIssueID;
            setSelectedRow();
        }

        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(tableModel);
        testTable.setRowSorter(tableRowSorter);
        tableRowSorter.setComparator(5, new Comparator<String>() {

            @Override
            public int compare(String name1, String name2) {
                 MyDateTime date1= new MyDateTime(name1);
                MyDateTime date2 = new MyDateTime(name2);
                return date1.compareTo(date2);
            }
        });
        tableRowSorter.setComparator(6, new Comparator<String>() {

            @Override
            public int compare(String name1, String name2) {
                MyDateTime date1= new MyDateTime(name1);
                MyDateTime date2 = new MyDateTime(name2);
                return date1.compareTo(date2);
            }
        });

        setSelectedRow();
        tableModel.fireTableDataChanged();

    }

    private void setSelectedRow() {
        /// Find the row in here
        int row = 0;
        for (TestDTO testDTO : testDTOs) {
            if (testDTO.issueID.equals(selectedIssueIDString) &&
                    testDTO.subIssueID.equals(selectedSubIssueIDString)) {
                break;
            }
            row = row + 1;
        }
        if (row >= testDTOs.size())
            row = testDTOs.size() - 1;
        Log.write(Log.Level.Debug, "", " selected " + row + " " + selectedIssueIDString +
                   "  " + selectedSubIssueIDString);
        if (row > 0)
            testTable.setRowSelectionInterval(row, row);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        setUpTable();
        /*

         */
    }
}
