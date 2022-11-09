package kenpugh.TestRecorder;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kenpugh.TestRecorder.Database.DatabaseSetup;
import kenpugh.TestRecorder.Database.TestDataAccess;
import kenpugh.TestRecorder.DomainTerms.MyFileSystem;
import kenpugh.TestRecorder.DomainTerms.MyString;
import kenpugh.TestRecorder.DomainTerms.Name;
import kenpugh.TestRecorder.Entities.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EntitiesStepDefinitions {

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
            String value = ConfigurationDTO.values.get(cv.variable);
            assertEquals(cv.value, value);

        }
    }

    @Given("file exists")
    public void file_exists(List<FileExistsValue> dataTable) {
        for (FileExistsValue fev : dataTable){
            MyFileSystem.create(fev.filePath, fev.contents);
            String result = MyFileSystem.read(fev.filePath);
            assertEquals(fev.contents, result);
        }
    }
    @DataTableType
    public StepDefinitions.TestRunDisplay inputTestRunDisplay(Map<String, String> entry) {
        StepDefinitions.TestRunDisplay trd = new StepDefinitions.TestRunDisplay();
        trd.testRunScript = entry.get("Test Script");
        return trd;
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
    @Given("database is setup")
    public void database_is_setup() {
        DatabaseSetup.setup();
        DatabaseSetup.removeTables();
        DatabaseSetup.setupTables();

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
    @When("test is stored")
    public void test_is_stored(List<TestDTO> dataTable) {
        for (TestDTO tDTO: dataTable){
            TestDataAccess.addTest(tDTO);
        }
    }
}
