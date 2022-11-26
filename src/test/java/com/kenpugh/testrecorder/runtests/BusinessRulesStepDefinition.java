package com.kenpugh.testrecorder.runtests;

import com.kenpugh.testrecorder.entities.Test;
import com.kenpugh.testrecorder.entities.TestDTO;
import com.kenpugh.testrecorder.entities.TestRun;
import com.kenpugh.testrecorder.entities.TestRunDTO;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


class UpdateTestFromTestRunBusinessRuleDTO {
    String oldLastResult;
    String oldDatePreviousResult;
    String oldDateLastRun;
    String result;
    String dateTime;
    String newLastResult;
    String newDateLastRun;
    String newDatePreviousResult;

    @Override
    public String toString() {
        return "UpdateTestFromTestRunBusinessRuleDTO{" +
                "oldLastResult='" + oldLastResult + '\'' +
                ", oldDatePreviousResult='" + oldDatePreviousResult + '\'' +
                ", oldDateLastRun='" + oldDateLastRun + '\'' +
                ", result='" + result + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", result='" + result + '\'' +
                ", newLastResult='" + newLastResult + '\'' +
                ", newDateLastRun='" + newDateLastRun + '\'' +
                ", newDatePreviousResult='" + newDatePreviousResult + '\'' +
                '}';
    }
}

public class BusinessRulesStepDefinition {
    Test oldTestForSequence;

    private static TestDTO getOldTestDTO(UpdateTestFromTestRunBusinessRuleDTO update) {
        TestDTO oldTestDTO = new TestDTO();
        oldTestDTO.lastResult = update.oldLastResult;
        oldTestDTO.dateLastRun = update.oldDateLastRun;
        oldTestDTO.datePreviousResult = update.oldDatePreviousResult;
        return oldTestDTO;
    }

    private static TestDTO getNewTestDTO(UpdateTestFromTestRunBusinessRuleDTO update) {
        TestDTO newTestDTO = new TestDTO();
        newTestDTO.lastResult = update.newLastResult;
        newTestDTO.dateLastRun = update.newDateLastRun;
        newTestDTO.datePreviousResult = update.newDatePreviousResult;
        return newTestDTO;
    }

    private static TestRunDTO getTestRunDTO(UpdateTestFromTestRunBusinessRuleDTO update) {
        TestRunDTO testRunDTO = new TestRunDTO();
        testRunDTO.result = update.result;
        testRunDTO.dateTime = update.dateTime;
        return testRunDTO;
    }

    @DataTableType
    public UpdateTestFromTestRunBusinessRuleDTO
    inputUpdateTestFromTestRunBusinessRuleDTO(Map<String, String> entry) {
        UpdateTestFromTestRunBusinessRuleDTO update =
                new UpdateTestFromTestRunBusinessRuleDTO();

        update.oldLastResult = entry.get("Old Last Result");
        update.oldDateLastRun = entry.get("Old Date Last Run");
        update.oldDatePreviousResult = entry.get("Old Date Previous Result");
        update.result = entry.get("Result");
        update.dateTime = entry.get("Date Time");
        update.newLastResult = entry.get("New Last Result");
        update.newDateLastRun = entry.get("New Date Last Run");
        update.newDatePreviousResult = entry.get("New Date Previous Result");
        return update;
    }

    @Given("Update Test from Test Run")
    public void update_test_from_test_run(List<UpdateTestFromTestRunBusinessRuleDTO> dataTable) {
        for (UpdateTestFromTestRunBusinessRuleDTO update : dataTable) {
            TestDTO oldTestDTO = getOldTestDTO(update);
            TestRunDTO testRunDTO = getTestRunDTO(update);
            TestDTO newTestDTO = getNewTestDTO(update);
            Test oldTest = Test.testFromDTO(oldTestDTO);
            Test expectedNewTest = Test.testFromDTO(newTestDTO);
            TestRun testRun = TestRun.TestRunFromDTO(testRunDTO);
            oldTest.UpdateTestFromTestRun(testRun);
            assertEquals(expectedNewTest, oldTest);
        }
    }

    @Given("test exists with")
    public void test_exists_with(List<Test> dataTable) {
        oldTestForSequence = dataTable.get(0);
    }

    @Given("Update Test from Test Run Sequence")
    public void update_test_from_test_run_sequence(List<UpdateTestFromTestRunBusinessRuleDTO> dataTable) {
        for (UpdateTestFromTestRunBusinessRuleDTO update : dataTable) {
            TestRunDTO testRunDTO = getTestRunDTO(update);
            TestDTO newTestDTO = getNewTestDTO(update);
            Test expectedNewTest = Test.testFromDTO(newTestDTO);
            TestRun testRun = TestRun.TestRunFromDTO(testRunDTO);
            oldTestForSequence.UpdateTestFromTestRun(testRun);
            assertEquals(expectedNewTest, oldTestForSequence);

        }
    }

}
