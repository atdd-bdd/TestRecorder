package kenpugh.TestRecorder.UI;


import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyFileSystem;
import kenpugh.TestRecorder.DomainTerms.MyString;
import kenpugh.TestRecorder.Entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;


public class TestRecorderFormSwing {


    private JPanel aPanel;
    private JPanel aTable;
    private JButton addTest;
    private JButton runTest;
    private JButton showHistory;
    private JScrollPane aScrollPaneForTable;
    private JTable testTable;
    private DefaultTableModel tableModel;
    static TestRecorderFormSwing testRecorderFormSwing;

    List<TestDTO> testDTOs;

    public TestRecorderFormSwing() {
        runTest.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = testTable.getSelectedRow();
                if (row < 0) {
                    System.err.println("Bad selection");
                    return;
                }

                TestDTO selectedTestDTO = testDTOs.get(row);
                TestRun testRun = TestRun.getBaseTestRun(
                        new IssueID(selectedTestDTO.issueID));
                TestRunDialog dialog = new TestRunDialog();
                dialog.testRunDTO = testRun.getDTO();
                dialog.scriptText = MyFileSystem.read(new MyString(selectedTestDTO.filePath));
                dialog.initializeData();
                dialog.pack();
                dialog.setVisible(true);
                if (dialog.added) {
                    testRun = TestRun.TestRunFromDTO(dialog.testRunDTO);
                    TestCollection.findTestAndUpdate(testRun.issueID, testRun);
                    testRecorderFormSwing.updateData();
                }

            }
        });
        addTest.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                TestEntryDialog dialog = new TestEntryDialog();
                dialog.pack();
                dialog.setVisible(true);
                if (dialog.testDTO != null){
                    Test test = Test.testFromDTO(dialog.testDTO);
                    TestCollection.addTest(test);
                    testRecorderFormSwing.updateData();
                }

            }
        });
        showHistory.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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

    public static void main(String[] args) {
        Configuration.loadFromFile();
        setUIFont(new javax.swing.plaf.FontUIResource(new Font("MS Mincho", Font.PLAIN, 16)));
        JFrame frame = new JFrame("TestRecorderFormSwing");
        testRecorderFormSwing = new TestRecorderFormSwing();
        frame.setContentPane(testRecorderFormSwing.aPanel);
        if (!kenpugh.TestRecorder.Entities.Configuration.formNotCloseOnExit) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        frame.pack();
        frame.setVisible(true);
        testRecorderFormSwing.updateData();
    }

    private void setUpTable() {
        //  super(new GridLayout(1,0));
        String[] columnNames = {
                "Issue ID",
                "Name",
                "Runner",
                "Last Result",
                "Date Last Run",
                "Date Previous Result",
                "File Path ",
                "Comments"
        };
        Vector<String> columnHeaders = new Vector<>(List.of(columnNames));
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnHeaders);
        testTable = new JTable(tableModel);
        testTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        testTable.setFillsViewportHeight(true);

    }

    public void updateData() {
        List<Test> tests = TestCollection.getAll();
        testDTOs = TestCollection.listTestDTOfromListTest(tests);
        tableModel.setNumRows(0);
        for (TestDTO testDTO : testDTOs) {
            String[] data = new String[8];
            data[0] = testDTO.issueID;
            data[1] = testDTO.name;
            data[2] = testDTO.runner;
            data[3] = testDTO.lastResult;
            data[4] = testDTO.datePreviousResult;
            data[5] = testDTO.dateLastRun;
            data[6] = testDTO.filePath;
            data[7] = testDTO.comments;
            tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();
        if (testDTOs.size() >= 1) {
            testTable.setRowSelectionInterval(0, 0);
        }

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        setUpTable();
        /*

         */
    }
}
