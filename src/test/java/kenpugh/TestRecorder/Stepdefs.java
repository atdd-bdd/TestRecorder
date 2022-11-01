package kenpugh.TestRecorder;

import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class Stepdefs {
class TestRunDisplay {
    String testRunScript = "";
}
    @DataTableType
    public TestRunDisplay inputTestRunDisplay(Map<String, String> entry) {
        TestRunDisplay trd = new TestRunDisplay();
        trd.testRunScript = entry.get("Test Script");
        return trd;
    }

@DataTableType
public ConfigurationValue inputConfigurationValue(Map<String, String> entry) {
    ConfigurationValue crv = new ConfigurationValue();
    crv.variable = entry.get("Variable");
    crv.value = entry.get("Value");
    return crv;
}
    @DataTableType
    public FileExistsValue inputFileExistsValue(Map<String, String> entry) {
        FileExistsValue fev = new FileExistsValue();
        fev.filePath = new MyString(entry.get("File Path"));
        fev.contents = entry.get("Contents");
        return fev;
    }

    // | Issue ID  | Name  | Last Result  | Runner | Date Last Run  | Date Previous Result  | File Path |
    @DataTableType
    public Test inputTest(Map<String, String> entry) {
        TestDTO testDTO = new TestDTO();
        testDTO.issueID = entry.get("Issue ID");
        testDTO.name = entry.get("Name");
        testDTO.lastResult = entry.get("Last Result");
        testDTO.runner = entry.get("Runner");
        testDTO.dateLastRun = entry.get("Date Last Run");
        testDTO.datePreviousResult = entry.get("Date Previous Result");
        testDTO.filePath = entry.get("File Path");
        testDTO.comments = entry.get("Comments");
        Test test = new Test();
        test.fromDTO(testDTO);
        return test;
    }

    @DataTableType
    public TestRun inputTestRun(Map<String, String> entry) {
        TestRunDTO testRunDTO = new TestRunDTO();
        testRunDTO.issueID = entry.get("Issue ID");
        testRunDTO.runner = entry.get("Runner");
        testRunDTO.dateTime= entry.get("Date Time");
        testRunDTO.comments = entry.get("Comments");
        testRunDTO.testResult = entry.get("Result");
        TestRun testRun = new TestRun();
        testRun.fromDTO(testRunDTO);
        return testRun;
    }

    @Given("configuration values are:")
    public void configuration_values_are(List<ConfigurationValue> dataTable) {
    for (ConfigurationValue cv : dataTable){
        System.out.println(" Configuration " + cv.variable + " " + cv.value);
        Configuration.setValue(cv.variable, cv.value);
    }
    }
    @Given("file exists")
    public void file_exists(List<FileExistsValue> dataTable) {
        for (FileExistsValue fev : dataTable){
            System.out.println(" FileExistsValue " +
                     " " + fev.contents);
            MyFileSystem.create(fev.filePath, fev.contents);
            String result = MyFileSystem.read(fev.filePath);
            assertEquals(fev.contents, result);
        }
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

    @Given("tests currently are")
    public void tests_currently_are(List<Test> dataTable) {
    System.out.println("Currently are");
        for (Test t: dataTable){
            System.out.println(t);
        }
    System.out.println("End currently are");
        assertArrayEquals(dataTable.toArray(), TestCollection.getAll().toArray());
    }
    @When("adding a test")
    public void adding_a_test(@Transpose List<Test> dataTable) {
        for (Test t: dataTable){
           System.out.println("Adding " + t);
           TestCollection.addTest(t);
        }
    }
    @Then("tests now are")
    public void tests_now_are(List<Test> dataTable) {
        System.out.println("Tests are now");
        for (Test t : TestCollection.getAll()) {
            System.out.println(t);
        }
        System.out.println("End tests are now");
        assertArrayEquals(dataTable.toArray(), TestCollection.getAll().toArray());
    }
    @Given("test exists")
    public void test_exists(List<Test> dataTable) {
        TestCollection.addTest(dataTable.get(0));
    }
    Test currentTest;
    @When("test is run")
    public void test_is_run(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        currentTest = TestCollection.findTest(tr.issueID);
        currentTest.updateWithTestRun(tr);
        TestCollection.updateTest(currentTest);
        System.out.println(" **** Test Run is " + tr);
    }
    @When("test run display contains")
    public void test_is_run_display_contains(@Transpose List<TestRunDisplay> dataTable) {
        String expected = dataTable.get(0).testRunScript;
        System.out.println(" Display script is " + expected);
        String actual = MyFileSystem.read(currentTest.filePath);
        assertEquals(expected, actual);
      }

    @Then("test is now")
    public void test_is_now(List<Test> dataTable) {
        Test expected = dataTable.get(0);
        System.out.println(" Test should be " + expected);
        Test actual = TestCollection.findTest(currentTest.issueID);
        assertEquals(expected, actual);

    }

    class DomainTermValid {
        String value;

        @Override
        public String toString() {
            return "DomainTermValid{" +
                    "value='" + value + '\'' +
                    ", valid='" + valid + '\'' +
                    ", notes='" + notes + '\'' +
                    '}';
        }

        String valid;
        String notes;

    }
    @DataTableType
    public DomainTermValid inputIssueIDValid(Map<String, String> entry) {
        DomainTermValid crv = new DomainTermValid();
        crv.value = entry.get("Value");
        crv.valid = entry.get("Valid");
        crv.notes = entry.get("Notes");
        return crv;
    }
    // | Value   | Valid  | Notes       |

    @Given("IssueID must be five characters and digits without spaces")
    public void issue_id_must_be_five_characters_and_digits_without_spaces
        (List<DomainTermValid> dataTable) {
        for (DomainTermValid dtv : dataTable) {
            System.out.println(dtv);
            IssueID temp = new IssueID(dtv.value);
            if (dtv.valid.equals("Yes")) {
                assertEquals(dtv.notes, dtv.value, temp.toString());
            } else {
                assertEquals(dtv.notes, IssueID.NOT_SPECIFIED, temp.toString());
            }
        }
    }

    @Given("TestDate must have valid format")
    public void test_date_must_have_valid_format
        (List<DomainTermValid> dataTable) {
        for (DomainTermValid dtv : dataTable){
            System.out.println("**** " + dtv);
            MyDateTime temp = MyDateTime.parse(dtv.value);
            System.out.println("*** " + temp);

            if (dtv.valid.equals("Yes")) {
                assertEquals( dtv.value, temp.toStringWithNever());
            }
            else {
                assertEquals(new MyDateTime().toString(), temp.toString());
            }
        }
    }


}
