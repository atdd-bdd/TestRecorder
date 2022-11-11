package kenpugh.TestRecorder;


import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kenpugh.TestRecorder.Database.TestDataAccess;
import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyFileSystem;
import kenpugh.TestRecorder.Entities.Test;
import kenpugh.TestRecorder.Entities.TestCollection;
import kenpugh.TestRecorder.Entities.TestRun;
import kenpugh.TestRecorder.Services.CurrentDateTimeService;
import kenpugh.TestRecorder.Services.CurrentUserService;

import java.util.List;

import static org.junit.Assert.*;

public class StepDefinitions {
    IssueID currentIssueID;

    @Given("tests are empty")
    public void tests_are_empty(List<Test> ignoredDataTable) {
        TestDataAccess.deleteAll();
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
        for (Test test : dataTable) {
            TestCollection.addTest(test);
        }
    }

    @When("test is selected")
    public void test_is_selected(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        currentIssueID = tr.getIssueID();
    }

    @When("test is run")
    public void test_is_run(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        tr.setIssueID(currentIssueID);
        tr.setDateTime(CurrentDateTimeService.getCurrentDateTime());
        tr.setRunner(CurrentUserService.getCurrentUser());
        TestCollection.findTestAndUpdate(tr.getIssueID(), tr);
    }

    @When("test run display contains")
    public void test_is_run_display_contains(@Transpose List<TestRunDisplay> dataTable) {
        String expected = dataTable.get(0).testRunScript;
        Test test = TestCollection.findTest(currentIssueID);
        String actual = MyFileSystem.read(test.getFilePath());
        assertEquals(expected, actual);
    }

    @Then("test is now")
    public void test_is_now(List<Test> dataTable) {
        Test expected = dataTable.get(0);
        Test actual = TestCollection.findTest(currentIssueID);
        assertEquals(expected, actual);
    }

    @Then("test run record is now")
    public void test_run_record_is_now(List<TestRun> dataTable) {
        for (TestRun tr : dataTable) {
            System.out.println("Test Run " + tr);
        }
    }

    static class TestRunDisplay {
        String testRunScript = "";
    }


}
