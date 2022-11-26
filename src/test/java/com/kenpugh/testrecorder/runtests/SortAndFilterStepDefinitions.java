package com.kenpugh.testrecorder.runtests;

import com.kenpugh.testrecorder.domainterms.TestStatus;
import com.kenpugh.testrecorder.entities.*;
import com.kenpugh.testrecorder.log.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SortAndFilterStepDefinitions {

    List<Test> unfiltered;
    List<Test> filtered;
    TestFilter testFilter;

    @Given("unfiltered tests are")
    public void unfiltered_tests_are(List<Test> dataTable) {
        unfiltered = dataTable;
    }

    @When("filtered by")
    public void filtered_by(Map<String, String> dataTable) {
        TestFilterDTO testFilterDTO = new TestFilterDTO();
        for (Map.Entry<String, String> entry : dataTable.entrySet()) {
            TestStatus testStatus = TestStatus.parse(entry.getKey());
            String value = entry.getValue();
            switch (testStatus) {
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
                    Log.write(Log.Level.Severe, " Cannot convert ", testStatus.toString());
            }
            testFilter = TestFilter.fromTestFilterDTO(testFilterDTO);
            filtered = TestCollection.filter(unfiltered, testFilter);
        }
    }

    @Then("filtered tests are")
    public void filtered_tests_are(List<Test> dataTable) {
        TestUseFields testUserFields = EntitiesStepDefinitions.testUseFields;

        assertTrue(compareSelectivelyTestLists(dataTable, filtered,
                testUserFields));
        // assertArrayEquals(dataTable.toArray(), filtered.toArray());
    }

    private boolean compareSelectivelyTestLists(List<Test> expectedList, List<Test> actualList, TestUseFields testUserFields) {
        boolean match = true;
        if (expectedList.size() != actualList.size()) {
            Log.write(Log.Level.Severe, " Sizes differ ", " ");
            match = false;
        }
        Test[] expected = new Test[expectedList.size()];
        Test[] actual = new Test[actualList.size()];
        expected = expectedList.toArray(expected);
        actual = actualList.toArray(actual);
        for (int index = 0; index < expected.length; index++) {
            if (!expected[index].selectiveEquals(actual[index], testUserFields)) {
                Log.write(Log.Level.Severe, " Expected " + expected[index], " Actual " + actual[index]);
                match = false;
            }
        }

        return match;
    }


}

