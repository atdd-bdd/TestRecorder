package kenpugh.TestRecorder.entities;


import kenpugh.TestRecorder.database.TestRunDataAccess;
import kenpugh.TestRecorder.domainTerms.IssueID;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestRunCollection {

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
    static public boolean addTestRun(@NotNull TestRun value) {
        TestRunDTO TestRunDTO = value.getDTO();
        return TestRunDataAccess.addTestRun(TestRunDTO);
    }

    @NotNull
    static public List<TestRun> findTestRuns(IssueID issueID) {
        List<TestRunDTO> testRunDTOs = TestRunDataAccess.findByIssueID(issueID);
            return listTestRunFromListTestDTORun(testRunDTOs);
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

    @SuppressWarnings("SpellCheckingInspection")
    static public List<TestRunDTO> listTestRunDTOfromListTestRun(List<TestRun> testRuns) {
        List<TestRunDTO> testRunDTOs = new ArrayList<>();
        for (TestRun testRun : testRuns) {
            TestRunDTO temp = testRun.getDTO();
            testRunDTOs.add(temp);
        }
        return testRunDTOs;
    }

    static public List<TestRun> listTestRunFromListTestDTORun(List<TestRunDTO> testDTORuns) {
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

