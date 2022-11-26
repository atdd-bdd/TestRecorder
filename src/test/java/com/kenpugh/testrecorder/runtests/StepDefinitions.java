package com.kenpugh.testrecorder.runtests;


import com.kenpugh.testrecorder.database.TestDataAccess;
import com.kenpugh.testrecorder.database.TestRunDataAccess;
import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.MyFileSystem;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.entities.*;
import com.kenpugh.testrecorder.log.Log;
import com.kenpugh.testrecorder.services.CurrentDateTimeService;
import com.kenpugh.testrecorder.services.CurrentUserService;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class StepDefinitions {
    IssueID currentIssueID;
    SubIssueID currentSubIssueID;

    @Given("tests are empty")
    public void tests_are_empty(List<Test> ignoredDataTable) {
        TestDataAccess.deleteAll();
        TestRunDataAccess.deleteAll();
    }


    @Given("tests currently are")
    public void tests_currently_are(List<Test> dataTable) {

        assertArrayEquals(dataTable.toArray(), TestCollection.getAll().toArray());
    }

    @When("adding a test")
    public void adding_a_test(@Transpose List<Test> dataTable) {
        for (Test t : dataTable) {
            if (!TestCollection.addTest(t))
                fail("Unable to add test");
        }
    }

    @When("adding a test that already exists")
    public void adding_a_test_that_already_exists(@Transpose List<Test> dataTable) {
        TestCollection.addTest(dataTable.get(0));

    }

    @Then("tests now are")
    public void tests_now_are(List<Test> dataTable) {
        assertArrayEquals(dataTable.toArray(), TestCollection.getAll().toArray());
    }

    @Given("test exists")
    public void test_exists(List<Test> dataTable) {
        if (!TestCollection.addTest(dataTable.get(0))) {
            if (!TestCollection.updateTest(dataTable.get(0))) {
                fail("Unable to update existing test");
            }
        }
    }

    @Given("tests are")
    public void tests_are(List<Test> dataTable) {
        TestCollection.deleteAll();
        TestRunCollection.deleteAll();
        for (Test test : dataTable) {
            TestCollection.addTest(test);
        }
    }

    @When("test is selected")
    public void test_is_selected(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        currentIssueID = tr.getIssueID();
        currentSubIssueID = tr.getSubIssueID();
    }

    @When("test is run")
    public void test_is_run(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        tr.setIssueID(currentIssueID);
        tr.setSubIssueID(currentSubIssueID);
        tr.setDateTime(CurrentDateTimeService.getCurrentDateTime());
        tr.setRunner(CurrentUserService.getCurrentUser());
        TestRunandTestCollaborator.findTestAndUpdateWithTestRun(tr.getIssueID(), tr.getSubIssueID(), tr);
        TestRunCollection.addTestRun(tr);
    }

    @When("test run display contains")
    public void test_is_run_display_contains(@Transpose List<TestRunDisplay> dataTable) {
        String expected = dataTable.get(0).testRunScript;
        Test test = TestCollection.findTest(currentIssueID, currentSubIssueID);
        String actual = MyFileSystem.read(test.getFilePath());
        assertEquals(expected, actual);
    }

    @Then("test is now")
    public void test_is_now(List<Test> dataTable) {
        Test expected = dataTable.get(0);
        Test actual = TestCollection.findTest(currentIssueID, currentSubIssueID);
        assertEquals(expected, actual);
    }

    @Then("test run records exist")
    public void test_run_records_exist(List<TestRun> dataTable) {
        List<TestRun> results = TestRunCollection.findTestRuns(currentIssueID, currentSubIssueID);

        assertTrue(arrayContains(dataTable.toArray(), results.toArray()));


    }

    private boolean arrayContains(Object[] expectedArray, Object[] actualArray) {
        boolean containsAll = true;
        for (Object expected : expectedArray) {
            boolean contains = false;
            for (Object actual : actualArray) {
                if (expected.equals(actual)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                Log.write(Log.Level.Debug, " Missing Array element ", expected.toString());
                containsAll = false;
            }
        }
        return containsAll;
    }

    @Given("test runs are empty")
    public void test_runs_are_empty() {
        TestRunCollection.deleteAll();
    }

    static class TestRunDisplay {
        String testRunScript = "";
    }


}
