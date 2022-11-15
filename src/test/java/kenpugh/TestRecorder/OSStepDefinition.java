package kenpugh.TestRecorder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kenpugh.TestRecorder.os.EnvironmentVariables;

import java.util.Map;

import static org.junit.Assert.assertEquals;



public class OSStepDefinition {
    @When("environment variable is set")
    public void environment_variable_is_set(Map<String, String> dataTable) {
        for (Map.Entry<String,String> entry : dataTable.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            EnvironmentVariables.setenv(key, value);
        }
    }
    @Then("environment variable is now")
    public void environment_variable_is_now(Map<String, String> dataTable) {
        for (Map.Entry<String,String> entry : dataTable.entrySet()) {
            String key = entry.getKey();
            String expected = entry.getValue();
            String actual = EnvironmentVariables.getenv(key);
            assertEquals(expected, actual);
        }
    }

}
