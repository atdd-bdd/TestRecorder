package kenpugh.TestRecorder.UI;

import kenpugh.TestRecorder.Entities.Test;
import kenpugh.TestRecorder.Entities.TestDTO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

public class TestTableSwing extends JPanel {
    JTable table;
    List<Test> tests;
  //  List<TestDTO> testDTOs;
    DefaultTableModel tableModel = new DefaultTableModel();
    public TestTableSwing() {
        super(new GridLayout(1,0));

        String[] columnNames = {
                "Issue ID",
                "Name",
                "Runner",
                "Date Last Run",
                "Date Previous Result",
                "File Path ",
                "Comments"
                };
        Vector<String> columnHeaders = new Vector<>(List.of(columnNames));
        tableModel.setColumnIdentifiers(columnHeaders);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    public void updateData () {
         for (Test test : tests) {
            TestDTO testDTO = test.getDTO();
            String[] data = new String[8];
            data[0] = testDTO.issueID;
            data[1] = testDTO.name;
            data[2] = testDTO.runner;
            data[3] = testDTO.lastResult;
            data[4] = testDTO.datePreviousResult;
            data[5] = testDTO.dateLastRun;
            data[6] = testDTO.filePath;
            data[7] = testDTO.comments;

            this.tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public  static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TestTableSwing newContentPane = new TestTableSwing();
        Class c = TestTableSwing.class;
        System.out.println((" Table type is " + c));
        if (c.isAssignableFrom(JPanel.class))
            System.out.println("IS Assignable");
        else
            System.out.println("NOT Assignable");

        System.out.println("table interface is " + c.getInterfaces());
        System.out.println("table super is " + c.getSuperclass());

        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

