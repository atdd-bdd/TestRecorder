package com.kenpugh.testrecorder.entities;


import com.kenpugh.testrecorder.database.TestRunDataAccess;
import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
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
    static public List<TestRun> findTestRuns(IssueID issueID, SubIssueID subIssueID) {
        List<TestRunDTO> testRunDTOs = TestRunDataAccess.findByIssueID(issueID, subIssueID);
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


    static public List<TestRunDTO> listTestRunDTOFromListTestRun(List<TestRun> testRuns) {
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

