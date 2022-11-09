package kenpugh.TestRecorder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kenpugh.TestRecorder.UI.TestRecorderFormSwing;

public class UIStepDefinitions {

    @When("test table swing is shown for {int} seconds")
    public void test_table_swing_is_shown_for_seconds(Integer int1) {

        TestRecorderFormSwing.main(null);
        try {
            Thread.sleep(int1 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Showing the table");
    }
    @When("test table swing is shown")
    public void test_table_swing_is_shown() {
        //new TestTableSwingTest().show();

        TestRecorderFormSwing.main(null);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Showing the table");
    }
    @Then("test table should show that data")
    public void test_table_should_show_that_data() {
        System.out.println("Look as the table");
    }

}
