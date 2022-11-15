package kenpugh.TestRecorder.Entities;


import kenpugh.TestRecorder.Database.TestDataAccess;
import kenpugh.TestRecorder.Database.TestRunDataAccess;
import kenpugh.TestRecorder.DomainTerms.IssueID;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestRunCollection {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static public boolean addTestRun(TestRun value) {
        TestRunDTO TestRunDTO = value.getDTO();
        return TestRunDataAccess.addTestRun(TestRunDTO);
    }

    @NotNull
    static public List<TestRun> findTestRuns(IssueID issueID) {
        List<TestRunDTO> testRunDTOs = TestRunDataAccess.findByIssueID(issueID);
            return listTestRunfromListTestDTORun(testRunDTOs);
    }

    static public List<TestRun> getAll() {
        List<TestRunDTO> listTestRunDTO = TestRunDataAccess.getAll();
        List<TestRun> listTestRun = new ArrayList<>();
        for (TestRunDTO tDTO : listTestRunDTO) {
            TestRun t = TestRun.TestRunFromDTO(tDTO);
            listTestRun.add(t);
        }
        return listTestRun;
    }

    static public List<TestRunDTO> listTestRunDTOfromListTestRun(List<TestRun> testRuns) {
        List<TestRunDTO> testRunDTOs = new ArrayList<>();
        for (TestRun testRun : testRuns) {
            TestRunDTO temp = testRun.getDTO();
            testRunDTOs.add(temp);
        }
        return testRunDTOs;
    }

    static public List<TestRun> listTestRunfromListTestDTORun(List<TestRunDTO> testDTORuns) {
        List<TestRun> testRuns = new ArrayList<>();
        for (TestRunDTO testRunDTO : testDTORuns) {
            TestRun temp = TestRun.TestRunFromDTO(testRunDTO);
            testRuns.add(temp);
        }
        return testRuns;
    }



    public static void deleteAll() {
        TestRunDataAccess.deleteAll();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("TestRunCollection{ \n");
        for (TestRun t : TestRunCollection.getAll()) {
            s.append(t.toString());
            s.append("\n");
        }
        s.append("}");
        return s.toString();
    }


}

