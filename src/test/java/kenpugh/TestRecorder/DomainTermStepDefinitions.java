package kenpugh.TestRecorder;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.DomainTerms.MyDateTime;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DomainTermStepDefinitions {

    @DataTableType
    public DomainTermValid inputIssueIDValid(Map<String, String> entry) {
        DomainTermValid crv = new DomainTermValid();
        crv.value = entry.get("Value");
        crv.valid = entry.get("Valid");
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
                assertEquals(dtv.value, temp.toStringWithNever());
            } else {
                assertEquals(new MyDateTime().toString(), temp.toString());
            }
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


}
