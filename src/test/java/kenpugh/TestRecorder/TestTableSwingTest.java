package kenpugh.TestRecorder;

import kenpugh.TestRecorder.UI.TestTableSwing;

public class TestTableSwingTest {
    public TestTableSwing testTableSwing;
    public void show(){
        /*
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                testTableSwing = new TestTableSwing();
                testTableSwing.createAndShowGUI();
            }
        });

         */
        testTableSwing = new TestTableSwing();
        testTableSwing.createAndShowGUI();
        System.out.println(" End of the show");
    }
}
