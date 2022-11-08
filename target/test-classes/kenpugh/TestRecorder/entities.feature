Feature: Entities

Scenario: Save and Load Configuration
Given configuration values are:
| Variable | Value |
| rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target |
| useTestDoubleForDateTime | false |
| useTestDoubleForRunner   | true  |
| valueTestDoubleForDateTime | Oct 1, 2022, 12:30:02 AM |
| valueTestDoubleForRunner | Jane |
When configuration is saved
And configuration is loaded
Then configuration values now are:
| Variable | Value |
| rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target |
| useTestDoubleForDateTime | false |
| useTestDoubleForRunner   | true  |
| valueTestDoubleForDateTime |Oct 1, 2022, 12:30:02 AM |
| valueTestDoubleForRunner | Jane |

Scenario: Can Store and Load Test from Database
    Given database is setup
    When test is stored
      | Issue ID  | Name               | Runner   | Last Result    | Date Last Run  | Date Previous Result  | File Path                | Comments |
      | 12345     | Enter test result  | No Name  |Failure         | Never          | Never                 | EnterTestResult.feature  | No Comment |
    Then test can be loaded
      | Issue ID  | Name               | Runner   | Last Result    | Date Last Run  | Date Previous Result  | File Path                | Comments |
      | 12345     | Enter test result  | No Name  |Failure         | Never          | Never                 | EnterTestResult.feature  | No Comment |
