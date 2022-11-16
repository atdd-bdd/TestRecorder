Feature: Test Recorder

  Background:
    Given configuration values are:
      | Variable     | Value                                           |
      | rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target |
    Given file exists
      | File Path               | Contents                              |
      | EnterTestResult.feature | Select test \n Run it \n Check result |

  Scenario: Add a test
    Given tests are empty
      | Issue ID | Name | Last Result | Runner | Date Last Run | Date Previous Result | File Path | Comments |
    When adding a test
      | Issue ID  | 12345                   |
      | Name      | Enter test result       |
      | File Path | EnterTestResult.feature |
    Then tests now are
      | Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |

    @doing
  Scenario: Run a test successfully
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
      | Comments | Works great |
    Then test run display contains
      | Test Script | Select test \n Run it \n Check result |
    And test is now
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
      | 12345    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
    And test run records exist
      | Issue ID | Date Time                | Result  | Comments    | Runner |
      | 12345    | Oct 1, 2022, 12:30:01 AM | Success | Works great | Sam    |

  Scenario: Run a test unsuccessfully
    Given test exists
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
      | 12345    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
    And  configuration values are:
      | Variable                   | Value                    |
      | useTestDoubleForDateTime   | true                     |
      | useTestDoubleForRunner     | true                     |
      | valueTestDoubleForDateTime | Oct 2, 2022, 12:31:01 AM |
      | valueTestDoubleForRunner   | Bill                     |

  #Test is run by bringing up a dialog box, entering appropriate information, and saving
    When test is selected
      | Issue ID | 12345       |
    And test is run
      | Result   | Failure       |
      | Comments | Something bad |
    Then test run display contains
      | Test Script | Select test \n Run it \n Check result |
    Then test is now
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result     | File Path               | Comments      |
      | 12345    | Enter test result | Bill   | Failure     | Oct 2, 2022, 12:31:01 AM | Oct 1, 2022, 12:30:01 AM | EnterTestResult.feature | Something bad |


