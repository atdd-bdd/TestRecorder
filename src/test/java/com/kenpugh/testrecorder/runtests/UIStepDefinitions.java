package com.kenpugh.testrecorder.runtests;

import io.cucumber.java.Transpose;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.kenpugh.testrecorder.entities.TestRun;
import com.kenpugh.testrecorder.ui.TestRecorderFormSwing;

import java.util.List;

public class UIStepDefinitions {

    @When("test table swing is shown for {int} seconds")
    public void test_table_swing_is_shown_for_seconds(Integer int1) {

        TestRecorderFormSwing.main(null);
        try {
            Thread.sleep(int1 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("test table swing is shown")
    public void test_table_swing_is_shown() {

        TestRecorderFormSwing.main(null);
        waitForInProgress();
    }

    @SuppressWarnings("BusyWait")
    private static void waitForInProgress() {
        while (TestRecorderFormSwing.inProgress) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @When("test table swing is shown with test run data")
    public void test_table_swing_is_shown_with_test_data(@Transpose List<TestRun> dataTable) {
        System.err.println(" Enter the following into the appropriate form");
        for (TestRun testRun: dataTable) {
            System.err.println(" Result " + testRun.getTestResult());
            System.err.println(" Comments " + testRun.getComments());
        }

        TestRecorderFormSwing.main(null);
        waitForInProgress();
        }
    @When("run the program")
    public void run_the_program() {
        TestRecorderFormSwing.main(null);
    }

    @Then("test table should show that data")
    public void test_table_should_show_that_data() {
        System.out.println("Look as the table");
    }

}
