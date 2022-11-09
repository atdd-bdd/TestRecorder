package kenpugh.TestRecorder;


import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import kenpugh.TestRecorder.Database.TestDataAccess;
import kenpugh.TestRecorder.DomainTerms.*;
import kenpugh.TestRecorder.Entities.*;
import kenpugh.TestRecorder.Services.CurrentDateTimeService;
import kenpugh.TestRecorder.Services.CurrentUserService;


import java.util.List;


import static org.junit.Assert.*;

public class StepDefinitions {
static class TestRunDisplay {
    String testRunScript = "";
}

    @Given("Test Results are")
    public void test_results_are(List<String> dataTable) {
         int count = TestResult.values().length;
         if (dataTable.size() != count)
             assertFalse("Count of enums did not match ", false);
        for (String cv : dataTable){
             try {
                TestResult tr = TestResult.valueOf(cv);
                assertEquals(cv, tr.toString());
            }
            catch (IllegalArgumentException e) {
                assertFalse(" Value " + cv, false);
            }
        }

    }

    @Given("tests are empty")
    public void tests_are_empty(List<Test> ignoredDataTable) {
        TestDataAccess.deleteAll();
    }


    @Given("tests currently are")
    public void tests_currently_are(List<Test> dataTable) {
        for (Test t: dataTable){
            System.out.println(t);
        }
        assertArrayEquals(dataTable.toArray(), TestCollection.getAll().toArray());
    }
    @When("adding a test")
    public void adding_a_test(@Transpose List<Test> dataTable) {
        for (Test t: dataTable){
           if (!TestCollection.addTest(t))
               fail("Unable to add test");
        }
    }
    @Then("tests now are")
    public void tests_now_are(List<Test> dataTable) {
        assertArrayEquals(dataTable.toArray(), TestCollection.getAll().toArray());
    }
    @Given("test exists")
    public void test_exists(List<Test> dataTable) {
        if (!TestCollection.addTest(dataTable.get(0))) {
            if (!TestCollection.updateTest(dataTable.get(0))){
                fail("Unable to update existing test");
            }
        }
    }
    @Given("tests are")
    public void tests_are(List<Test>  dataTable) {
        TestCollection.deleteAll();
        for (Test test : dataTable){
            TestCollection.addTest(test);
        }
    }
    IssueID currentIssueID;
    @When("test is selected")
    public void test_is_selected(@Transpose List<TestRun> dataTable ) {
        TestRun tr = dataTable.get(0);
        currentIssueID = tr.issueID;
    }


    @When("test is run")
    public void test_is_run(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        tr.issueID = currentIssueID;
        tr.dateTime = CurrentDateTimeService.getCurrentDateTime();
        tr.runner = CurrentUserService.getCurrentUser();
        TestCollection.findTestAndUpdate(tr.issueID, tr);
    }
    @When("test run display contains")
    public void test_is_run_display_contains(@Transpose List<TestRunDisplay> dataTable) {
        String expected = dataTable.get(0).testRunScript;
        Test test = TestCollection.findTest(currentIssueID);
        String actual = MyFileSystem.read(test.filePath);
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
        for (TestRun tr: dataTable){
            System.out.println("Test Run " + tr);
        }
    }







}
