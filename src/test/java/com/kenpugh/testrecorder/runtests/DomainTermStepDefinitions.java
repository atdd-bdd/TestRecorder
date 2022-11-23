package com.kenpugh.testrecorder.runtests;

import com.kenpugh.testrecorder.domainterms.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DomainTermStepDefinitions {

    @DataTableType
    public DomainTermValid inputIssueIDValid(Map<String, String> entry) {
        DomainTermValid crv = new DomainTermValid();
        crv.value = entry.get("Value");
        crv.valid = entry.get("Valid");
        crv.notes = entry.get("Notes");
        return crv;
    }

    @DataTableType
    public DomainTermChanges inputDomainTermChanges(Map<String, String> entry) {
        DomainTermChanges crv = new DomainTermChanges();
        crv.value = entry.get("Value");
        crv.newValue = entry.get("New Value");
        crv.notes = entry.get("Notes");
        return crv;
    }


    @Given("IssueID must be five characters and digits without spaces")
    public void issue_id_must_be_five_characters_and_digits_without_spaces
            (List<DomainTermValid> dataTable) {
        for (DomainTermValid dtv : dataTable) {
            IssueID temp = new IssueID(dtv.value);
            if (dtv.valid.equals("Yes")) {
                assertEquals(dtv.notes, dtv.value, temp.toString());
            } else {
                assertEquals(dtv.notes, IssueID.NOT_SPECIFIED, temp.toString());
            }
        }
    }
    // | Value   | Valid  | Notes       |

    @Given("TestDate must have valid format")
    public void test_date_must_have_valid_format
            (List<DomainTermValid> dataTable) {
        for (DomainTermValid dtv : dataTable) {
            MyDateTime temp = MyDateTime.parse(dtv.value);

            if (dtv.valid.equals("Yes")) {
                assertEquals(dtv.value, temp.toStringWithSymbols());
            } else {
                assertEquals(new MyDateTime().toString(), temp.toString());
            }
        }
    }

    static class DomainTermChanges {
        String value;
        String newValue;
        String notes;

        @Override
        public String toString() {
            return "DomainTermChanges{" +
                    "value='" + value + '\'' +
                    ", newValue='" + newValue + '\'' +
                    ", notes='" + notes + '\'' +
                    '}';
        }
    }
    static class DomainTermValid {
        String value;
        String valid;
        String notes;

        @Override
        public String toString() {
            return "DomainTermValid{" +
                    "value='" + value + '\'' +
                    ", valid='" + valid + '\'' +
                    ", notes='" + notes + '\'' +
                    '}';
        }

    }
    @Given("Test Results are")
    public void test_results_are(List<String> dataTable) {
        int count = TestResult.values().length;
        if (dataTable.size() != count)
            assertFalse("Count of enums did not match ", false);
        for (String cv : dataTable) {
            try {
                TestResult tr = TestResult.valueOf(cv);
                assertEquals(cv, tr.toString());
            } catch (IllegalArgumentException e) {
                assertFalse(" Value " + cv, false);
            }
        }

    }


    @Given("Test Statuses are")
    public void test_statuses_are(List<String> dataTable) {
            int count = TestStatus.values().length;
            if (dataTable.size() != count)
                assertFalse("Count of enums did not match ", false);
            for (String cv : dataTable) {
                try {
                    TestStatus tr = TestStatus.valueOf(cv);
                    assertEquals(cv, tr.toString());
                } catch (IllegalArgumentException e) {
                    assertFalse(" Value " + cv, false);
                }
            }
           }

    @Given("IssueID must be three characters and digits without spaces")
    public void issue_id_must_be_three_characters_and_digits_without_spaces(List<DomainTermValid> dataTable) {
            for (DomainTermValid dtv : dataTable) {
                SubIssueID temp = new SubIssueID(dtv.value);
                if (dtv.valid.equals("Yes")) {
                    assertEquals(dtv.notes, dtv.value, temp.toString());
                } else {
                    assertEquals(dtv.notes, SubIssueID.NOT_SPECIFIED, temp.toString());
                }
            }
        }
    @Given("Name changes are")
    public void name_changes_are(List<DomainTermChanges> dataTable) {
            for (DomainTermChanges dtv : dataTable) {
                Name revised = new Name(dtv.value);
                assertEquals(dtv.notes, dtv.newValue, revised.toString());
            }
    }
    @Given("MyString changes are")
    public void my_string_changes_are(List<DomainTermChanges> dataTable) {
        for (DomainTermChanges dtv : dataTable) {
            MyString revised = new MyString(dtv.value);
            assertEquals(dtv.notes, dtv.newValue, revised.toString());
        }
    }



}
