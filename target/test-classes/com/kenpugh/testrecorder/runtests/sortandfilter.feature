#noinspection CucumberUndefinedStep
Feature:  Sort and Filter


 Scenario: Filter
    Given unfiltered tests are
     | Issue ID | SubIssueID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result    | File Path               | Comments    | Test Status |
      | 12345    | 123        |Name a            | Sam    | Success     | Oct 1, 2022, 12:30:00 AM | Sept 30, 2022, 12:30:00 AM| EnterTestResult.feature | Works great | Active |
      | 12348    | 123        |Name B            | Bill   | Failure     | Oct 1, 2022, 12:30:01 AM | Sept 30, 2022, 12:30:04 AM               | EnterTestResult.feature | Works great | Inactive |
      | 12347    | 234        |Mame c            | Jane   | Success     | Oct 1, 2022, 12:30:02 AM | Sept 30, 2022, 12:30:02 AM                | EnterTestResult.feature | Works great | Active  |
      | 12344    | 456        |Name D            | Wanda  | Failure     | Oct 1, 2022, 12:30:03 AM | Sept 30, 2022, 12:30:03 AM | EnterTestResult.feature | Works great | Retired                |
    When filtered by
      | Active   | True  |
      | Inactive | False |
      | Retired  | False |
    Then filtered tests are
     | Issue ID | SubIssueID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result    | File Path               | Comments    | Test Status |
      | 12345    | 123        |Name a            | Sam    | Success     | Oct 1, 2022, 12:30:00 AM | Sept 30, 2022, 12:30:00 AM| EnterTestResult.feature | Works great | Active |
      | 12347    | 234        |Mame c            | Jane   | Success     | Oct 1, 2022, 12:30:02 AM | Sept 30, 2022, 12:30:02 AM                | EnterTestResult.feature | Works great | Active  |

Scenario: Filter with Selective Comparison
  Given unfiltered tests are
     | Issue ID | SubIssueID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result    | File Path               | Comments    | Test Status |
     | 12345    | 123        |Name a            | Sam    | Success     | Oct 1, 2022, 12:30:00 AM | Sept 30, 2022, 12:30:00 AM| EnterTestResult.feature | Works great | Active |
     | 12348    | 123        |Name B            | Bill   | Failure     | Oct 1, 2022, 12:30:01 AM | Sept 30, 2022, 12:30:04 AM               | EnterTestResult.feature | Works great | Inactive |
     | 12347    | 234        |Mame c            | Jane   | Success     | Oct 1, 2022, 12:30:02 AM | Sept 30, 2022, 12:30:02 AM                | EnterTestResult.feature | Works great | Active  |
     | 12344    | 456        |Name D            | Wanda  | Failure     | Oct 1, 2022, 12:30:03 AM | Sept 30, 2022, 12:30:03 AM | EnterTestResult.feature | Works great | Retired                |
   When filtered by
     | Active   | True  |
     | Inactive | False |
     | Retired  | False |
   Then filtered tests are
     | Issue ID | SubIssueID | Test Status |
     | 12345    | 123        | Active |
     | 12347    | 234        | Active  |
