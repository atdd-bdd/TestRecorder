package com.kenpugh.testrecorder.runtests;

import com.kenpugh.testrecorder.domainterms.TestStatus;
import com.kenpugh.testrecorder.entities.Test;
import com.kenpugh.testrecorder.entities.TestCollection;
import com.kenpugh.testrecorder.entities.TestFilter;
import com.kenpugh.testrecorder.entities.TestFilterDTO;
import com.kenpugh.testrecorder.log.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

public class SortAndFilterStepDefinitions {

    List<Test> unfiltered;
    List<Test> filtered;
    TestFilter testFilter;
    @Given("unfiltered tests are")
    public void unfiltered_tests_are(List<Test> dataTable) {
        unfiltered = dataTable;
        System.out.println("Unfiltered are " + unfiltered.toString());
    }

        @When("filtered by")
        public void filtered_by(Map<String, String> dataTable) {
            TestFilterDTO testFilterDTO = new TestFilterDTO();
            for (Map.Entry<String,String> entry : dataTable.entrySet()){
                TestStatus testStatus = TestStatus.parse(entry.getKey());
                String value = entry.getValue();
               switch(testStatus) {
                   case Active:
                       testFilterDTO.includeActive = value;
                       break;
                   case Inactive:
                       testFilterDTO.includeInactive = value;
                       break;
                   case Retired:
                       testFilterDTO.includeRetired = value;
                       break;
                   default:
                       Log.write(Log.Level.Severe, " Cannot convert " , testStatus.toString());
               }
               testFilter = TestFilter.fromTestFilterDTO(testFilterDTO);
                System.out.println(" Filter is " + testFilter );
                filtered = TestCollection.filter(unfiltered, testFilter);
            }
        }

        @Then("filtered tests are")
        public void filtered_tests_are(List<Test> dataTable) {
            assertArrayEquals(dataTable.toArray(), filtered.toArray());

            System.out.println("Filtered are " + unfiltered.toString());
        }


    }

