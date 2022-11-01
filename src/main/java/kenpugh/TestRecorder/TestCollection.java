package kenpugh.TestRecorder;

import java.util.ArrayList;
import java.util.List;

public class TestCollection {
    static List<Test> values = new ArrayList<Test> ();  // for initial test
   static public void addTest(Test value){
       values.add(value);

    }
    static public Test findTest(IssueID issueID){
       for (Test t: values){
           if (t.issueID.equals(issueID))
               return t;
       }
        return null;
    }
    static public List<Test> getAll() {
        List<Test> list ;
        list = values;
        return list;
    }

    public static void updateTest(Test updatedTest) {
        for (Test t: values){
            System.out.println(" Checking " + t.issueID + " " + updatedTest.issueID );
            if (t.issueID.equals(updatedTest.issueID)) {
                t = updatedTest;
                return;
            }
        }
        System.err.println(" Could not find test to update ");
    }
}

