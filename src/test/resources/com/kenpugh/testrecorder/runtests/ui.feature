Feature: UI
  Background:
    Given configuration values are:
      | Variable     | Value                                           |
      | rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target\ |
      | useTestDoubleForDateTime   | true                     |
      | useTestDoubleForRunner     | true                     |
      | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:01 AM |
      | valueTestDoubleForRunner   | Sam                      |
      | formNotCloseOnExit         | true |

  @manual
  Scenario: Display Test Table
    Given tests are
      | Issue ID | SubIssueID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result    | File Path               | Comments    | Test Status |
      | 12345    | 123        |Name a            | Sam    | Success     | Oct 1, 2022, 12:30:00 AM | Sept 30, 2022, 12:30:00 AM| EnterTestResult.feature | Works great | Active |
      | 12348    | 123        |Name B            | Bill   | Failure     | Oct 1, 2022, 12:30:01 AM | Sept 30, 2022, 12:30:04 AM               | EnterTestResult.feature | Works great | Inactive |
      | 12347    | 234        |Mame c            | Jane   | Success     | Oct 1, 2022, 12:30:02 AM | Sept 30, 2022, 12:30:02 AM                | EnterTestResult.feature | Works great | Active  |
      | 12344    | 456        |Name D            | Wanda  | Failure     | Oct 1, 2022, 12:30:03 AM | Sept 30, 2022, 12:30:03 AM | EnterTestResult.feature | Works great | Retired                |
    And test table swing is shown
    Then test table should show that data

@manual
Scenario: Run a test successfully
  Given tests are
   | Issue ID | SubIssueID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               |
    | 12345    | 678        |Enter test result | No Name | Failure     | Never         | Never                | EnterTestResult.feature |
  When test is selected
   | Issue ID | 12345 |
   | SubIssueID | 678       |
  And test table swing is shown with test run data
     | Result   | Success     |
     | Comments | Works great |
Then test is now
  | Issue ID | SubIssueID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result | File Path               | Comments    |
    | 12345  |678           | Enter test result | Sam    | Success     | Oct 1, 2022, 12:30:01 AM | Never                | EnterTestResult.feature | Works great |

  @manual @setuprun
  Scenario: Run the program
    Given tests are
     | Issue ID | SubIssueID | Name              | Runner  | Last Result | Date Last Run | Date Previous Result | File Path               |
      | 12345    | 678        |Enter test result | No Name | Failure     | Never         | Never                | EnterTestResult.feature |
    And  configuration values are:
      | Variable                   | Value                    |
      | useTestDoubleForDateTime   | false                    |
      | useTestDoubleForRunner     | false                    |
      | rootFilePath               | C:\Users\KenV1\IdeaProjects\TestRecorder\target\ |
      | formNotCloseOnExit         | false |
    When test table swing is shown
    # Execution works as in non-test world