package kenpugh.TestRecorder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TestTableSwing extends JPanel {
    private boolean DEBUG = false;
    List<Test> tests;
    List<TestDTO> testDTOs;

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
//| Issue ID  | Name               | Runner   | Last Result     | Date Last Run  | Date Previous Result  | File Path                |
//| 12345     | Enter test result  | No Name  | Failure         | Never          | Never                 | EnterTestResult.feature  |

        Object[][] data = {
                {"12345", "Enter test result",
                        "No Name","Failure", "Never", "Never","EnterTestResult.feature ", "No comment" }
          };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
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

