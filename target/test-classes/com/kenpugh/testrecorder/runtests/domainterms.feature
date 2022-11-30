Feature: Domain Terms

  Scenario: Domain Term TestResult
    * Test Results are
      | Success |
      | Failure |

  Scenario: Domain Term IssueID
    * IssueID must be five characters and digits without spaces
      | Value  | Valid | Notes      |
      | 12345  | Yes   |            |
      | A1234  | Yes   |            |
      | 1 123  | No    | Has spaces |
      | 1234   | No    | Too short  |
      | 123456 | No    | Too long   |

  Scenario: Domain Term TestDate
    * TestDate must have valid format
      | Value                    | Valid | Notes                              |
      | Oct 1, 2022, 12:30:01 AM | Yes   |                                    |
      | XXX 1, 2022, 12:30:01 AM | No    | Bad month                          |
      | Never                    | Yes   | Converted to Jan 1, 1970, 0:0:0 AM |
# plus more as desired

  Scenario: Domain Term TestStatus
    * Test Statuses are
      | Active   |
      | Inactive |
      | Retired  |

  Scenario: Domain Term SubIssueID
    * IssueID must be three characters and digits without spaces
      | Value | Valid | Notes      |
      | 123   | Yes   |            |
      | A12   | Yes   |            |
      | 1 1   | No    | Has spaces |
      | 12    | No    | Too short  |
      | 12345 | No    | Too long   |

  Scenario: Domain Term Name
    # Name can contain alphanumerics and spaces
    # Non-valid characters will be eliminated
    * Name changes are
      | Value  | New Value | Notes         |
      | Sam 1  | Sam 1     |               |
      | Sam ?1 | Sam 1     | ? not allowed |
   # Can have more

  Scenario: Domain Term MyString
    # MyString can contain alphanumerics and most punctuation -
    # non-valid characters will be eliminated
    * MyString changes are
      | Value     | New Value | Notes           |
      | String    | String    |                 |
      | String()$ | String    | ()? not allowed |
   # Can have more