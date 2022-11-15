package kenpugh.TestRecorder.UI;


import kenpugh.TestRecorder.domainTerms.IssueID;
import kenpugh.TestRecorder.domainTerms.MyDateTime;
import kenpugh.TestRecorder.domainTerms.MyFileSystem;
import kenpugh.TestRecorder.domainTerms.MyString;
import kenpugh.TestRecorder.entities.*;
import kenpugh.TestRecorder.log.Log;

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
    private DefaultTableModel tableModel;
    static TestRecorderFormSwing testRecorderFormSwing;

    List<TestDTO> testDTOs;

    public TestRecorderFormSwing() {
        runTestButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = testTable.getSelectedRow();
                if (row < 0) {
                    Log.write(Log.Level.Info, "Bad selection ", e.toString());
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
                    TestCollection.findTestAndUpdate(testRun.getIssueID(), testRun);
                    TestRunCollection.addTestRun(testRun);
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
                TestRunHistoryDialog dialog = new TestRunHistoryDialog();
                int index =  testTable.getSelectedRow();
                TestDTO testDTO = testDTOs.get(index);
                dialog.issueID = new IssueID(testDTO.issueID);
                dialog.updateData();
                dialog.pack();
                dialog.setVisible(true);
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
public static JFrame frame;
    public static void main(String[] args) {
        MyConfiguration.loadFromFile();
        setUIFont(new javax.swing.plaf.FontUIResource(new Font("MS Mincho", Font.PLAIN, 16)));
        frame = new JFrame("TestRecorderFormSwing");
        TestRecorderFormSwing.inProgress = true;
        testRecorderFormSwing = new TestRecorderFormSwing();
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
                System.err.println("Window is closing");
                TestRecorderFormSwing.inProgress = false;
            }
        });

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
            testDTO.dateLastRun = MyDateTime.toDisplayString(testDTO.dateLastRun);
            testDTO.datePreviousResult = MyDateTime.toDisplayString(testDTO.datePreviousResult);
            data[0] = testDTO.issueID;
            data[1] = testDTO.name;
            data[2] = testDTO.runner;
            data[3] = testDTO.lastResult;
            data[4] = testDTO.dateLastRun;
            data[5] = testDTO.datePreviousResult;
            data[6] = testDTO.filePath;
            data[7] = testDTO.comments;
            tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();
        if (testDTOs.size() >= 1) {
            testTable.setRowSelectionInterval(0, 0);
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
        tableRowSorter.setComparator(4, new Comparator<String>() {

            @Override
            public int compare(String name1, String name2) {
                MyDateTime date1= new MyDateTime(name1);
                MyDateTime date2 = new MyDateTime(name2);
                return date1.compareTo(date2);
            }
        });



    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        setUpTable();
        /*

         */
    }
}
