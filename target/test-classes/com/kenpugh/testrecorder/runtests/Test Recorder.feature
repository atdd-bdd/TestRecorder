Feature: Test Recorder

  Background:
    Given configuration values are:
      | Variable     | Value                                           |
      | rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target\ |
      | useTestDoubleForDateTime   | true                     |
      | useTestDoubleForRunner     | true                     |
      | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:01 AM |
      | valueTestDoubleForRunner   | Sam                      |
      |databaseURL                 |jdbc:hsqldb:hsql://localhost|
      |  databaseJDBCDriver        |org.hsqldb.jdbcDriver       |
      |databasePassword            |                            |
      | databaseUserID             |SA                          |
    Given file exists
      | File Path               | Contents                              |
      | EnterTestResult.feature | Select test \n Run it \n Check result |

  Scenario: Add a test
    Given tests are empty
     | Issue ID | Sub Issue ID | Name | Last Result | Runner | Date Last Run | Date Previous Result | File Path | Comments |
    When adding a test
      | Issue ID  | 12345                   |
      | Name      | Enter test result       |
      | Sub Issue ID | 678                  |
      | File Path | EnterTestResult.feature |
    Then tests now are
     | Issue ID | Sub Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | 678        | Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |

  Scenario: Run a test successfully
    Given test exists
     | Issue ID | Sub Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               | Comments |
      | 12345    | 678        |Enter test result |         | Failure     | Never         | Never                | EnterTestResult.feature |          |
    And   value for runner is
      | Sam                      |
   And  value for current date is
       | Oct 1, 2022, 12:30:01 AM |
  # Test is run by bringing up a dialog box, entering appropriate information, and saving
   When test is selected
   | Issue ID | 12345 |
      | Sub Issue ID | 678    |
    When test is run
      | Result   | Success     |
      | Comments | Works great |
    Then test run display contains
      | Test Script | Select test \n Run it \n Check result |
    And test is now
     | Issue ID | Sub Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
      | 12345    | 678        |Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
    And test run records exist
     | Issue ID | Sub Issue ID | Date Time                | Result  | Comments    | Runner |
     | 12345    | 678        |Oct 1, 2022, 12:30:01 AM | Success | Works great | Sam    |

  Scenario: Run a test unsuccessfully
    Given test exists
     | Issue ID | Sub Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
      | 12345    | 678        |Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
    And   value for runner is
      | Bill                     |
    And  value for current date is
      | Oct 2, 2022, 12:31:02 AM |
    When test is selected
     | Issue ID | 12345|
     | SubIssue ID | 678       |
    And test is run
      | Result   | Failure       |
      | Comments | Something bad |
    Then test run display contains
      | Test Script | Select test \n Run it \n Check result |
    Then test is now
     | Issue ID | Sub Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result     | File Path               | Comments      |
      | 12345    | 678        | Enter test result | Bill   | Failure     | Oct 2, 2022, 12:31:02 AM | Oct 1, 2022, 12:30:01 AM | EnterTestResult.feature | Something bad |
