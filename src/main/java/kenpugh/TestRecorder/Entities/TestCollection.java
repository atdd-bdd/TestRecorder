package kenpugh.TestRecorder.Entities;


import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.Database.TestDataAccess;

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
            return Test.NOT_FOUND;
        else {
            return Test.testFromDTO(testDTO);
        }
      }

    static public List<Test> getAll() {
        List<TestDTO> listTestDTO = TestDataAccess.getAll();
        List<Test> listTest = new ArrayList<>();
        for (TestDTO tDTO : listTestDTO){
            Test t = Test.testFromDTO(tDTO);
            listTest.add(t);
        }
        return listTest;
    }

/*
 When updates to tests be allowed?
 public static void updateTest(Test updatedTest) {
        TestDTO testDTO = updatedTest.getDTO();
        TestDataAccess.update(testDTO);
        }
*/
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("TestCollection{ \n");
         for (Test t : TestCollection.getAll()) {
            s.append(t.toString());
            s.append("\n");
        }
       s.append("}");
         return s.toString();
    }

    public static void findTestAndUpdate(IssueID issueID, TestRun tr) {
        Test t = findTest((issueID));
        if (t != Test.NOT_FOUND) {
            t.updateWithTestRun(tr);
            TestDTO testDTO = t.getDTO();
            TestDataAccess.update(testDTO);
        }
        else
            System.err.println(" Cannot update test - not found " + tr.issueID);
    }
}

