#noinspection CucumberUndefinedStep
Feature:  Sort and Filter

 @ignore
 Scenario: Filter

    Given tests are
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result    | File Path               | Comments    | Status |
      | 12345    | Name a            | Sam    | Success     | Oct 1, 2022, 12:30:00 AM | Sept 30, 2022, 12:30:00 AM| EnterTestResult.feature | Works great | Active |
      | 12348    | Name B            | Bill   | Failure     | Oct 1, 2022, 12:30:01 AM | Sept 30, 2022, 12:30:04 AM               | EnterTestResult.feature | Works great | Inactive |
      | 12347    | Mame c            | Jane   | Success     | Oct 1, 2022, 12:30:02 AM | Sept 30, 2022, 12:30:02 AM                | EnterTestResult.feature | Works great | Active  |
      | 12344    | Name D            | Wanda  | Failure     | Oct 1, 2022, 12:30:03 AM | Sept 30, 2022, 12:30:03 AM | EnterTestResult.feature | Works great | Retired                |
    When filtered by
      | Test Status | Active |
    Then filtered tests are
      | Issue ID | Name              | Runner | Last Result | Date Last Run            | Date Previous Result    | File Path               | Comments    | Status |
      | 12345    | Name a            | Sam    | Success     | Oct 1, 2022, 12:30:00 AM | Sept 30, 2022, 12:30:00 AM| EnterTestResult.feature | Works great | Active |
      | 12348    | Name B            | Bill   | Failure     | Oct 1, 2022, 12:30:01 AM | Sept 30, 2022, 12:30:04 AM               | EnterTestResult.feature | Works great | Inactive |
      | 12347    | Mame c            | Jane   | Success     | Oct 1, 2022, 12:30:02 AM | Sept 30, 2022, 12:30:02 AM                | EnterTestResult.feature | Works great | Active  |
      | 12344    | Name D            | Wanda  | Failure     | Oct 1, 2022, 12:30:03 AM | Sept 30, 2022, 12:30:03 AM | EnterTestResult.feature | Works great | Retired                |
