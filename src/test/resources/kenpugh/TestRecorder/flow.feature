Feature: Flow feature

  Background:
    Given configuration values are:
      | Variable     | Value                                           |
      | rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target |
    Given file exists
      | File Path                 | Contents |
      | Different test.feature    | Not used |

  Scenario: Add a test that already exists does not change it
    Given tests are
      | Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |
    When adding a test that already exists
      | Issue ID  | 12345                   |
      | Name      | Different Name           |
      | File Path | Different test.feature  |
    Then tests now are
      | Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |

  Scenario: Run a test successfully multiple times
    Given test exists
      | Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |
    And  configuration values are:
      | Variable                   | Value                    |
      | useTestDoubleForDateTime   | true                     |
      | useTestDoubleForRunner     | true                     |
      | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:01 AM |
      | valueTestDoubleForRunner   | Sam                      |

  # Test is run by bringing up a dialog box, entering appropriate information, and saving
   When test is selected
      | Issue ID | 12345       |
    When test is run
      | Result   | Success     |
      | Comments | OK Stuff       |
    When Configuration test double value for current date is
    |  Oct 1, 2022, 12:30:02 AM |
      #   When  configuration values are:
      # | Variable                   | Value                    |
      # | useTestDoubleForDateTime   | true                     |
      #| valueTestDoubleForDateTime | Oct 1, 2022, 12:30:02 AM |
    And test is run
      | Result   | Failure     |
      | Comments | Bad stuff   |
    When  configuration values are:
      | Variable                   | Value                    |
      | useTestDoubleForDateTime   | true                     |
      | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:03 AM |
    When test is run
      | Result   | Success     |
      | Comments | Works great |
    And test is now
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result     | File Path               | Comments    |
      | 12345    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:03 AM | Oct 1, 2022, 12:30:02 AM | EnterTestResult.feature | Works great |

