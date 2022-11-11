package kenpugh.TestRecorder;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import kenpugh.TestRecorder.Entities.Test;
import kenpugh.TestRecorder.Entities.TestDTO;
import kenpugh.TestRecorder.Entities.TestRun;
import kenpugh.TestRecorder.Entities.TestRunDTO;

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

    private static TestDTO getoldTestDTO(UpdateTestFromTestRunBusinessRuleDTO upftrbr) {
        TestDTO oldTestDTO = new TestDTO();
        oldTestDTO.lastResult = upftrbr.oldLastResult;
        oldTestDTO.dateLastRun = upftrbr.oldDateLastRun;
        oldTestDTO.datePreviousResult = upftrbr.oldDatePreviousResult;
        return oldTestDTO;
    }

    private static TestDTO getnewTestDTO(UpdateTestFromTestRunBusinessRuleDTO upftrbr) {
        TestDTO newTestDTO = new TestDTO();
        newTestDTO.lastResult = upftrbr.newLastResult;
        newTestDTO.dateLastRun = upftrbr.newDateLastRun;
        newTestDTO.datePreviousResult = upftrbr.newDatePreviousResult;
        return newTestDTO;
    }

    private static TestRunDTO getTestRunDTO(UpdateTestFromTestRunBusinessRuleDTO upftrbr) {
        TestRunDTO testRunDTO = new TestRunDTO();
        testRunDTO.testResult = upftrbr.result;
        testRunDTO.dateTime = upftrbr.dateTime;
        return testRunDTO;
    }

    @DataTableType
    public UpdateTestFromTestRunBusinessRuleDTO
    inputUpdateTestFromTestRunBusinessRuleDTO(Map<String, String> entry) {
        UpdateTestFromTestRunBusinessRuleDTO upftrbr =
                new UpdateTestFromTestRunBusinessRuleDTO();

        upftrbr.oldLastResult = entry.get("Old Last Result");
        upftrbr.oldDateLastRun = entry.get("Old Date Last Run");
        upftrbr.oldDatePreviousResult = entry.get("Old Date Previous Result");
        upftrbr.result = entry.get("Result");
        upftrbr.dateTime = entry.get("Date Time");
        upftrbr.newLastResult = entry.get("New Last Result");
        upftrbr.newDateLastRun = entry.get("New Date Last Run");
        upftrbr.newDatePreviousResult = entry.get("New Date Previous Result");
        return upftrbr;
    }

    @Given("Update Test from Test Run")
    public void update_test_from_test_run(List<UpdateTestFromTestRunBusinessRuleDTO> dataTable) {
        for (UpdateTestFromTestRunBusinessRuleDTO upftrbr : dataTable) {
            System.out.println(upftrbr);
            TestDTO oldTestDTO = getoldTestDTO(upftrbr);
            TestRunDTO testRunDTO = getTestRunDTO(upftrbr);
            TestDTO newTestDTO = getnewTestDTO(upftrbr);
            Test oldTest = Test.testFromDTO(oldTestDTO);
            Test expectedNewTest = Test.testFromDTO(newTestDTO);
            TestRun testRun = TestRun.TestRunFromDTO(testRunDTO);
            oldTest.TestUpdatedFromTestRun(testRun);
            assertEquals(expectedNewTest, oldTest);
        }
    }

    @Given("test exists with")
    public void test_exists_with(List<Test> dataTable) {
        oldTestForSequence = dataTable.get(0);
    }

    @Given("Update Test from Test Run Sequence")
    public void update_test_from_test_run_sequence(List<UpdateTestFromTestRunBusinessRuleDTO> dataTable) {
        for (UpdateTestFromTestRunBusinessRuleDTO upftrbr : dataTable) {
            TestRunDTO testRunDTO = getTestRunDTO(upftrbr);
            TestDTO newTestDTO = getnewTestDTO(upftrbr);
            Test expectedNewTest = Test.testFromDTO(newTestDTO);
            TestRun testRun = TestRun.TestRunFromDTO(testRunDTO);
            oldTestForSequence.TestUpdatedFromTestRun(testRun);
            assertEquals(expectedNewTest, oldTestForSequence);

        }
    }

}
