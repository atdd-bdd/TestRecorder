package kenpugh.TestRecorder.Entities;


import kenpugh.TestRecorder.Database.TestDataAccess;
import kenpugh.TestRecorder.DomainTerms.IssueID;

import java.util.ArrayList;
import java.util.List;

public class TestCollection {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static public boolean addTest(Test value) {
        TestDTO testDTO = value.getDTO();
        return TestDataAccess.addTest(testDTO);
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
        for (TestDTO tDTO : listTestDTO) {
            Test t = Test.testFromDTO(tDTO);
            listTest.add(t);
        }
        return listTest;
    }

    static public List<TestDTO> listTestDTOfromListTest(List<Test> tests) {
        List<TestDTO> testDTOs = new ArrayList<>();
        for (Test test : tests) {
            TestDTO temp = test.getDTO();
            testDTOs.add(temp);
        }
        return testDTOs;
    }


    public static boolean updateTest(Test updatedTest) {
        TestDTO testDTO = updatedTest.getDTO();
        return TestDataAccess.update(testDTO);
    }

    public static void deleteAll() {
        TestDataAccess.deleteAll();
    }

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
            if (!TestDataAccess.update(testDTO))
                System.err.println("Error in updating test");
        } else
            System.err.println(" Cannot update test - not found " + tr.getIssueID());
    }
}

