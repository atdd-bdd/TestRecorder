Feature: Flow feature


  Background:
    Given configuration values are:
      | Variable     | Value                                           |
      | rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target\ |
      | useTestDoubleForDateTime   | true                     |
      | useTestDoubleForRunner     | true                     |
      | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:01 AM |
      | valueTestDoubleForRunner   | Sam                      |
   Given file exists
      | File Path                 | Contents |
      | Different test.feature    | Not used |

  Scenario: Add a test that already exists does not change it
    Given tests are
     | Issue ID | SubIssueID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | 678        |Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |
    When adding a test that already exists
      | Issue ID  | 12345                   |
      | Sub Issue ID | 678                  |
      | Name      | Different Name           |
      | File Path | Different test.feature  |
    Then tests now are
     | Issue ID | SubIssueID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | 678        |Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |

  Scenario: Run a test successfully multiple times
    Given test exists
     | Issue ID | SubIssueID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | 678        |Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |
    And   value for runner is
      | Sam                      |
    And  value for current date is
      | Oct 1, 2022, 12:30:01 AM |

  # Test is run by bringing up a dialog box, entering appropriate information, and saving
   When test is selected
     | Issue ID | 12345|
  | SubIssueID |678       |
    When test is run
      | Result   | Success     |
      | Comments | OK Stuff       |
    When  value for current date is
    |  Oct 1, 2022, 12:30:02 AM |
      And test is run
      | Result   | Failure     |
      | Comments | Bad stuff   |
    When  value for current date is
      |  Oct 1, 2022, 12:30:03 AM  |
    When test is run
      | Result   | Success     |
      | Comments | Works great |
    And test is now
     | Issue ID | SubIssueID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result     | File Path               | Comments    |
      | 12345    | 678        |Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:03 AM | Oct 1, 2022, 12:30:02 AM | EnterTestResult.feature | Works great |
    And test run records exist
     | Issue ID | SubIssueID | Date Time                | Result  | Comments    | Runner |
      | 12345    | 678        |Oct 1, 2022, 12:30:01 AM | Success | OK Stuff    | Sam    |
      | 12345    | 678        |Oct 1, 2022, 12:30:02 AM | Failure | Bad stuff   | Sam    |
      | 12345    | 678        |Oct 1, 2022, 12:30:03 AM | Success | Works great | Sam    |

