package kenpugh.TestRecorder;


import java.util.ArrayList;
import java.util.List;

public class TestCollection {

    static public void addTest(Test value) {
        TestDTO testDTO = value.getDTO();
        TestDataAccess.addTest(testDTO);
    }

    static public Test findTest(IssueID issueID) {
        TestDTO testDTO = TestDataAccess.findByIssueID(issueID);
        if (testDTO == TestDTO.NOT_FOUND)
            return null;
        else {
            Test test = new Test();
            test.fromDTO((testDTO));
            return test;
        }
      }

    static public List<Test> getAll() {
        List<TestDTO> listTestDTO = TestDataAccess.getAll();
        List<Test> listTest = new ArrayList<>();
        for (TestDTO tdto : listTestDTO){
            Test t = new Test();
            t.fromDTO((tdto));
            listTest.add(t);
        }
        return listTest;
    }

    public static void updateTest(Test updatedTest) {
        TestDTO testDTO = updatedTest.getDTO();
        TestDataAccess.update(testDTO);
        }

    @Override
    public String toString() {
        String s = "TestCollection{ \n";
         for (Test t : TestCollection.getAll()) {
            s += t.toString() + "\n";
        }
       s += "}";
         return s;
    }

    public static void findTestAndUpdate(IssueID issueID, TestRun tr) {
        Test t = findTest((issueID));
        if (t != null) {
            t.updateWithTestRun(tr);
            TestDTO testDTO = t.getDTO();
            TestDataAccess.update(testDTO);
        }
        else
            System.err.println(" Cannot update test - not found " + tr.issueID);
    }
}

