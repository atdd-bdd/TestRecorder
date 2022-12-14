package com.kenpugh.testrecorder.runtests;

import com.kenpugh.testrecorder.database.DatabaseSetup;
import com.kenpugh.testrecorder.database.TestDataAccess;
import com.kenpugh.testrecorder.domainterms.MyDateTime;
import com.kenpugh.testrecorder.domainterms.MyFileSystem;
import com.kenpugh.testrecorder.domainterms.MyString;
import com.kenpugh.testrecorder.domainterms.Name;
import com.kenpugh.testrecorder.entities.*;
import com.kenpugh.testrecorder.log.Log;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static com.kenpugh.testrecorder.runtests.StepDefinitionHelpers.setUseFieldFromEntryMap;
import static org.junit.Assert.*;

public class EntitiesStepDefinitions {


    public static TestUseFields testUseFields;

    private static boolean doesContain(List<TestDTO> entriesToMatch, List<TestDTO> entryCollection) {
        boolean match = false;
        for (TestDTO tDTO : entriesToMatch) {
            Test e = Test.testFromDTO(tDTO);
            match = false;
            for (TestDTO tDTOOther : entryCollection) {
                Test a = Test.testFromDTO(tDTOOther);
                if (e.equals(a)) {
                    match = true;
                    break;
                }
            }
            if (!match)
                break;
        }
        return match;
    }

    @DataTableType
    public MyConfigurationValue inputConfigurationValue(Map<String, String> entry) {
        MyConfigurationValue crv = new MyConfigurationValue();
        crv.variable = entry.get("Variable");
        if (!MyConfiguration.isValidVariable(crv.variable)) {
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
    public void configuration_values_are(List<MyConfigurationValue> dataTable) {
        for (MyConfigurationValue cv : dataTable) {
            MyConfigurationDTO.addToMap(cv.variable, cv.value);

        }
        MyConfiguration.fromDTO();
        MyConfiguration.saveToFile();
    }

    @When("configuration is saved")
    public void configuration_is_saved() {
        MyConfiguration.saveToFile();
    }

    @When("configuration is loaded")
    public void configuration_is_loaded() {
        MyConfiguration.valueTestDoubleForRunner = new Name("Should not be this");
        MyConfiguration.loadFromFile();
    }

    @Then("configuration values now are:")
    public void configuration_values_now_are(List<MyConfigurationValue> dataTable) {
        for (MyConfigurationValue cv : dataTable) {
            String value = MyConfigurationDTO.values.get(cv.variable);
            if (cv.value == null) {
                Log.write(Log.Level.Debug, " Configuration  for variable is null ",
                        cv.variable + " actual value is " + value);
                cv.value = "";
            }
            assertEquals(cv.value, value);

        }
    }

    @When("value for current date is")
    public void configuration_test_double_value_for_current_date_is(List<String> dataTable) {
        String currentDateValueString = dataTable.get(0);
        MyDateTime value = MyDateTime.parse(currentDateValueString);
        MyDateTime invalid = MyDateTime.parse(MyDateTime.NEVER_STRING);
        if (value.equals(invalid))
            Log.write(Log.Level.Info, "Bad time ", currentDateValueString);
        else {
            MyConfiguration.valueTestDoubleForDateTime = value;
            MyConfiguration.useTestDoubleForDateTime = true;
            MyConfiguration.saveToFile();
            Log.write(Log.Level.Debug, " DateTime is now ", value.toString());
        }

    }

    @When("value for runner is")
    public void configuration_test_double_value_for_runner_is(List<String> dataTable) {
        String currentRunnerString = dataTable.get(0);
        Name name = new Name(currentRunnerString);
        if (!currentRunnerString.equals(name.toString()))
            Log.write(Log.Level.Info, "Changed name ", name.toString());
        else {
            MyConfiguration.valueTestDoubleForRunner = name;
            MyConfiguration.useTestDoubleForRunner = true;
            MyConfiguration.saveToFile();
            Log.write(Log.Level.Debug, " Runner is now ", name.toString());
        }

    }

    @Given("file exists")
    public void file_exists(List<FileExistsValue> dataTable) {
        for (FileExistsValue fev : dataTable) {
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
        TestDTO testDTO = setTestDTOFromEntryMap(entry);
        testUseFields = setUseFieldFromEntryMap(entry);

        return Test.testFromDTO(testDTO);
    }

    @DataTableType
    @SuppressWarnings("UnnecessaryLocalVariable")
    public TestDTO inputTestDTO(Map<String, String> entry) {
        TestDTO testDTO = setTestDTOFromEntryMap(entry);
        return testDTO;
    }

    @DataTableType
    public TestRun inputTestRun(Map<String, String> entry) {
        TestRunDTO testRunDTO = setTestRunDTOFromEntryMap(entry);
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
        List<TestDTO> testDTOs = TestDataAccess.getAll();
        boolean match = doesContain(dataTable, testDTOs);
        if (!match)
            fail(" Loaded do not matched stored");
    }

    @When("test is stored")
    public void test_is_stored(List<TestDTO> dataTable) {
        for (TestDTO tDTO : dataTable) {
            TestDataAccess.addTest(tDTO);
        }
    }

    @Then("test is equal when selectively compared to")
    public void test_is_equal_when_selectively_compared_to(List<Test> dataTable) {
        Test compare = dataTable.get(0);
        List<Test> actuals = TestCollection.getAll();
        // can change any value in testDTO to test comparison
        TestDTO testDTO = actuals.get(0).getDTO();
        Test actual = Test.testFromDTO(testDTO);
        assertTrue(compare.selectiveEquals(actual, testUseFields));


    }

    public TestDTO setTestDTOFromEntryMap(Map<String, String> entryMap) {
        TestDTO testDTO = new TestDTO();
        for (Map.Entry<String, String> entry : entryMap.entrySet()) {
            StepDefinitionHelpers.setFieldFromKeyValue(testDTO, entry.getKey(), entry.getValue());
        }
        return testDTO;
    }

    public TestRunDTO setTestRunDTOFromEntryMap(Map<String, String> entryMap) {
        TestRunDTO testRunDTO = new TestRunDTO();
        for (Map.Entry<String, String> entry : entryMap.entrySet()) {
            StepDefinitionHelpers.setFieldFromKeyValue(testRunDTO, entry.getKey(), entry.getValue());
        }
        return testRunDTO;
    }


}