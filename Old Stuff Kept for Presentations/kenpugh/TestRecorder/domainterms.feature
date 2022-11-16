Feature: Domain Terms

  Scenario: Domain Term TestResult
    * Test Results are
      | Success  |
      | Failure  |

  Scenario: Domain Term IssueID
    * IssueID must be five characters and digits without spaces
      | Value   | Valid  | Notes       |
      | 12345   | Yes    |             |
      | A1234   | Yes    |             |
      | 1 123   | No     | Has spaces  |
      | 1234    | No     | Too short   |
      | 123456  | No     | Too long    |

  Scenario: Domain Term TestDate
    * TestDate must have valid format
      | Value                     | Valid  | Notes                               |
      | Oct 1, 2022, 12:30:01 AM  | Yes    |                                     |
      | XXX 1, 2022, 12:30:01 AM  | No     | Bad month                           |
      |  Never                    | Yes    | Converted to Jan 1, 1970, 0:0:0 AM  |
# plus more as desired
