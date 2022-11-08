package kenpugh.TestRecorder;

import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kenpugh.TestRecorder.Database.DatabaseSetup;
import kenpugh.TestRecorder.Database.TestDataAccess;
import kenpugh.TestRecorder.DomainTerms.*;
import kenpugh.TestRecorder.Entities.*;
import kenpugh.TestRecorder.Services.CurrentDateTimeService;
import kenpugh.TestRecorder.Services.CurrentUserService;
import kenpugh.TestRecorder.UI.TestRecorderFormSwing;

import java.util.Collection;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.*;

public class StepDefinitions {
static class TestRunDisplay {
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
    if (!Configuration.isValidVariable(crv.variable)) {
        fail(" Bad configuration variable");
    }
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
        return Test.testFromDTO(testDTO);
    }
    @DataTableType
    public TestDTO inputTestDTO(Map<String, String> entry) {
        TestDTO testDTO = new TestDTO();
        testDTO.issueID = entry.get("Issue ID");
        testDTO.name = entry.get("Name");
        testDTO.lastResult = entry.get("Last Result");
        testDTO.runner = entry.get("Runner");
        testDTO.dateLastRun = entry.get("Date Last Run");
        testDTO.datePreviousResult = entry.get("Date Previous Result");
        testDTO.filePath = entry.get("File Path");
        testDTO.comments = entry.get("Comments");
        return testDTO;
    }

    @DataTableType
    public TestRun inputTestRun(Map<String, String> entry) {
        TestRunDTO testRunDTO = new TestRunDTO();
        testRunDTO.issueID = entry.get("Issue ID");
        testRunDTO.runner = entry.get("Runner");
        testRunDTO.dateTime= entry.get("Date Time");
        testRunDTO.comments = entry.get("Comments");
        testRunDTO.testResult = entry.get("Result");
        return TestRun.TestRunFromDTO(testRunDTO);
    }

    @Given("configuration values are:")
    public void configuration_values_are(List<ConfigurationValue> dataTable) {
    for (ConfigurationValue cv : dataTable){
        System.out.println(" Configuration " + cv.variable + " " + cv.value);
        ConfigurationDTO.addToMap(cv.variable, cv.value);

    }
    Configuration.fromDTO();
    }


    @When("configuration is saved")
    public void configuration_is_saved() {
 Configuration.saveToFile();
    }
    @When("configuration is loaded")
    public void configuration_is_loaded() {
    Configuration.valueTestDoubleForRunner = new Name("Should not be this");
 Configuration.loadFromFile();
    }
    @Then("configuration values now are:")
    public void configuration_values_now_are(List<ConfigurationValue> dataTable) {
        for (ConfigurationValue cv : dataTable){
            System.out.println(" Configuration " + cv.variable + " " + cv.value);
           String value = ConfigurationDTO.values.get(cv.variable);
                    assertEquals(cv.value, value);

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

    @Given("tests are empty")
    public void tests_are_empty(List<Test> dataTable) {
        System.out.println(dataTable);
        TestDataAccess.deleteAll();
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
        Test currentTest;
    @When("test is run")
    public void test_is_run(@Transpose List<TestRun> dataTable) {
        TestRun tr = dataTable.get(0);
        currentTest = TestCollection.findTest(tr.issueID);
        tr.dateTime = CurrentDateTimeService.getCurrentDateTime();
        tr.runner = CurrentUserService.getCurrentUser();
        TestCollection.findTestAndUpdate(tr.issueID, tr);
    }
    @When("test run display contains")
    public void test_is_run_display_contains(@Transpose List<TestRunDisplay> dataTable) {
        String expected = dataTable.get(0).testRunScript;
        String actual = MyFileSystem.read(currentTest.filePath);
        assertEquals(expected, actual);
      }

    @Then("test is now")
    public void test_is_now(List<Test> dataTable) {
        Test expected = dataTable.get(0);
        Test actual = TestCollection.findTest(currentTest.issueID);
        assertEquals(expected, actual);
    }

    static class DomainTermValid {
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

    @Then("test run record is now")
    public void test_run_record_is_now(List<TestRun> dataTable) {
        for (TestRun tr: dataTable){
            System.out.println("Test Run " + tr);
        }
    }


    @Given("database is setup")
    public void database_is_setup() {
        DatabaseSetup.setup();
        DatabaseSetup.removeTables();
        DatabaseSetup.setupTables();

    }
    @When("test is stored")
    public void test_is_stored(List<TestDTO> dataTable) {
        for (TestDTO tDTO: dataTable){
            System.out.println(tDTO);
        TestDataAccess.addTest(tDTO);
        }
    }

    @Then("test can be loaded")
    public void test_can_be_loaded(List<TestDTO> dataTable) {
        Collection<TestDTO> testDTOs = TestDataAccess.getAll();
        boolean match = false;
        for (TestDTO tDTO: dataTable){
            Test e = Test.testFromDTO(tDTO);

            match = false;
            for (TestDTO tDTOOther : testDTOs){

                Test  a = Test.testFromDTO(tDTOOther);
                if (e.equals(a)){
                     match = true;
                    break;
                }
            }
            if (!match)
                break;
        }
        if (!match )
            fail(" Loaded do not matched stored");
    }

    @When("test table swing is shown")
    public void test_table_swing_is_shown() {
       //new TestTableSwingTest().show();

        TestRecorderFormSwing.main(null);
        try {
            Thread.sleep(100000);
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
