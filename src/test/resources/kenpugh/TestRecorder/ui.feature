Feature: UI

  @manual
  Scenario: Display Test Table
    Given tests are
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
      | 12345    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12346    | Enter test result | Bill   | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12347    | Enter test result | Jane   | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12348    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12340    | Enter test result | Bill   | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12350    | Enter test result | Jane   | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12351    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12352    | Enter test result | Bill   | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
      | 12347    | Enter test result | Jane   | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
    And  configuration values are:
      | Variable                   | Value                    |
      |formNotCloseOnExit        | false |
    And test table swing is shown for 5 seconds
    Then test table should show that data

@manual
Scenario: Run a test successfully
  Given tests are
    | Issue ID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               |
    | 12345    | Enter test result | No Name | Failure     | Never         | Never                | EnterTestResult.feature |
  And  configuration values are:
    | Variable                   | Value                    |
    | useTestDoubleForDateTime   | true                     |
    | useTestDoubleForRunner     | true                     |
    | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:01 AM |
    | valueTestDoubleForRunner   | Sam                      |
    | rootFilePath               | C:\Users\KenV1\IdeaProjects\TestRecorder\target |
    |formNotCloseOnExit        | true |
  When test is selected
    | Issue ID | 12345       |
  And test table swing is shown for 20 seconds
   # Test is run by bringing up a dialog box, entering appropriate information, and saving
# When test is run
 #  | Result   | Success     |
  # | Comments | Works great |
 #And test run display contains
 # | Test Script | Select test \n Run it \n Check result |
Then test is now
   | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
    | 12345    | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |
