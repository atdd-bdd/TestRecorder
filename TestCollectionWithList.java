package kenpugh.TestRecorder;
/*
import java.util.ArrayList;
import java.util.List;

public class TestCollectionWithList {

        static List<Test> values = new ArrayList<>();  // for initial test

        static public void addTest(Test value) {
            values.add(value);

        }

        static public Test findTest(IssueID issueID) {
            for (Test t : values) {
                if (t.issueID.equals(issueID)) return t;
            }
            return Test.NOT_FOUND;
        }

        static public List<Test> getAll() {
            List<Test> list;
            list = values;
            return list;
        }

        public static void updateTest(Test updatedTest) {
            for (Test t : values) {
 updatedTest.issueID);
                if (t.issueID.equals(updatedTest.issueID)) {
                    t = updatedTest;
                    return;
                }
            }
            System.err.println(" Could not find test to update ");
        }

        @Override
        public String toString() {
            String s = "TestCollection{ \n";
            for (Test t : kenpugh.TestRecorder.Entities.TestCollection.getAll()) {
                s += t.toString() + "\n";
            }
            s += "}";
            return s;
        }

        public static void findTestAndUpdate(IssueID issueID, TestRun tr) {
            Test t = findTest((issueID));
            if (t != null)
                t.updateWithTestRun(tr);
            else
                System.err.println(" Cannot update test - not found " + tr.issueID);
        }
    }



*/