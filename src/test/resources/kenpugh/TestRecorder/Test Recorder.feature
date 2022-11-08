Feature: Test Recorder
#Given configuration values are "RootFilePath" and "C:saffddsf "
Background:
Given configuration values are:
| Variable | Value |
| rootFilePath | C:\Users\KenV1\IdeaProjects\TestRecorder\target |
Given file exists
| File Path                |  Contents  |
| EnterTestResult.feature  | Select test \n Run it \n Check result |

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

  Scenario: Domain Term TestResult
* Test Results are 
| Success  |
| Failure  |

Scenario: Domain Term IssueID
* IssueID must be five characters and digits without spaces 
| Value   | Valid  | Notes       |
| 12345   | Yes    |             |
| A1234   | Yes    |             |
| 1 123   | No     | Has spaces  |
| 1234    | No     | Too short   |
| 123456  | No     | Too long    |

Scenario: Domain Term TestDate
* TestDate must have valid format
| Value                | Valid  | Notes                             |
| Oct 1, 2022, 12:30:01 AM  | Yes    |                                   |
| XXX 1, 2022, 12:30:01 AM    | No     | Bad month                         |
|  Never               | Yes    | Converted to Jan 1, 1970, 0:0:0 AM  |
# plus more as desired 

Scenario: Add a test 
Given tests are empty
| Issue ID  | Name  | Last Result  | Runner | Date Last Run  | Date Previous Result  | File Path |
When adding a test 
| Issue ID   | 12345                    |
| Name       | Enter test result        |
| File Path  | EnterTestResult.feature  |
Then tests now are 
| Issue ID  | Name               | Runner   | Last Result    | Date Last Run  | Date Previous Result  | File Path                |
| 12345     | Enter test result  | No Name  |Failure         | Never          | Never                 | EnterTestResult.feature  |

Scenario: Run a test successfully
Given test exists
| Issue ID  | Name               | Runner   | Last Result     | Date Last Run  | Date Previous Result  | File Path                |
| 12345     | Enter test result  | No Name  | Failure         | Never          | Never                 | EnterTestResult.feature  |
And  configuration values are:
  | Variable | Value |
  | useTestDoubleForDateTime | true  |
  | useTestDoubleForRunner   | true  |
  | valueTestDoubleForDateTime | Oct 1, 2022, 12:30:01 AM |
  | valueTestDoubleForRunner | Sam |

  # Test is run by bringing up a dialog box, entering appropriate information, and saving
 When test is run
| Issue ID   | 12345                    |
| Result     | Success                  |
| Comments   | Works great              |
And test run display contains
  | Test Script | Select test \n Run it \n Check result |
Then test is now
  | Issue ID  | Name               | Runner   | Last Result    | Date Last Run                   | Date Previous Result  | File Path                | Comments   |
  | 12345     | Enter test result  | Sam      | Success        | Oct 1, 2022, 12:30:01 AM        | Never                 | EnterTestResult.feature  | Works great              |
And test run record is now
  | Issue ID  | Date Time                 | Result   | Comments     | Runner  |
  | 12345     | Oct 1, 2022, 12:30:01 AM  | Success  | Works great  | Sam     |


  Scenario: Run a test unsuccessfully
    Given test exists
      | Issue ID  | Name               | Runner   | Last Result    | Date Last Run                   | Date Previous Result  | File Path                | Comments   |
      | 12345     | Enter test result  | Sam      | Success        | Oct 1, 2022, 12:30:01 AM        | Never                 | EnterTestResult.feature  | Works great              |
    And  configuration values are:
      | Variable | Value |
      | useTestDoubleForDateTime | true  |
      | useTestDoubleForRunner   | true  |
      | valueTestDoubleForDateTime | Oct 2, 2022, 12:31:01 AM  |
      | valueTestDoubleForRunner | Bill |

  #Test is run by bringing up a dialog box, entering appropriate information, and saving
    When test is run
      | Issue ID   | 12345                    |
      | Result     | Failure                  |
      | Comments   | Something bad            |
    And test run display contains
      | Test Script | Select test \n Run it \n Check result |
    Then test is now
      | Issue ID  | Name               | Runner    | Last Result    | Date Last Run                   | Date Previous Result  | File Path                | Comments   |
      | 12345     | Enter test result  | Bill      | Failure        | Oct 2, 2022, 12:31:01 AM     | Oct 1, 2022, 12:30:01 AM                    | EnterTestResult.feature  | Something bad                 |

    Scenario: Can Store and Load Test from Database
      Given database is setup
      When test is stored
      | Issue ID  | Name               | Runner   | Last Result    | Date Last Run  | Date Previous Result  | File Path                | Comments |
      | 12345     | Enter test result  | No Name  |Failure         | Never          | Never                 | EnterTestResult.feature  | No Comment |
      Then test can be loaded
        | Issue ID  | Name               | Runner   | Last Result    | Date Last Run  | Date Previous Result  | File Path                | Comments |
        | 12345     | Enter test result  | No Name  |Failure         | Never          | Never                 | EnterTestResult.feature  | No Comment |

  @doing
  Scenario: Display Test Table
    Given test exists
      | Issue ID  | Name               | Runner   | Last Result    | Date Last Run                   | Date Previous Result  | File Path                | Comments   |
      | 12345     | Enter test result  | Sam      | Success        | Oct 1, 2022, 12:30:01 AM        | Never                 | EnterTestResult.feature  | Works great              |
    When test table swing is shown
    Then test table should show that data
