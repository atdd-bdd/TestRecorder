Feature: Test Records 


Background: 
Given configuration values are:
| Root File Path |          |
Given file exists
| File Path                | Permissions  |
| EnterTestResult.feature  | Read         |
And file has contents
"""
Select a test to run 
Run it and record result with a comment 
Check that test record is updated 
""" 

Scenario: Domain Term TestResult
* Test Results are 
| Success  |
| Failure  |

Scenario: Domain Term IssueID
* IssueID must be five characters and digits without spaces 
| Value   | Valid  | Notes       |
| 12345   | Yes    |             |
| A1234   | Yes    |             |
| 1 1234  | No     | Has spaces  |
| 1234    | No     | Too short   |
| 123456  | No     | Too long    |



Scenario: Domain Term TestDate
* TestDate is YYYY-MM-DD HH:MM:SS   Never is 1900-01-01 00:00:00 
| Value                | Valid  | Notes                             |
| 2022-08-02 12:01:01  | Yes    |                                   |
| 2022-08 12:01:01     | No     | No day                            |
|  Never               | Yes    | Converted to 1900-01-01 00:00:00  |
# plus more as desired 

Scenario: Add a test 
Given tests currently are
| Issue ID  | Name  | Last Result  | Date Last Run  | Date Previous Result  | File Path |
When adding a test 
| Issue ID   | 12345                    |
| Name       | Enter test result        |
| File Path  | EnterTestResult.feature  |
Then tests now are 
| Issue ID  | Name               | Last Result    | Date Last Run  | Date Previous Result  | File Path                |
| 12345     | Enter test result  | Failure        | Never          | Never                 | EnterTestResult.feature  |


Scenario: Run a test successfully 
Given tests currently are
| Issue ID  | Name               | Last Result  | Date Last Run  | Date Previous Result  | File Path                |
| 12345     | Enter test result  | None         | Never          | Never                 | EnterTestResult.feature  |
When test is run 
| Issue ID   | 12345                |
| Date Time  | 2022-08-02 12:01:01  |
And results entered 
| Result   | Success      |
| Comment  | Works great  |
Then tests now are 
| Issue ID  | Name               | Last Result  | Date Last Run        | Date Previous Result  |
| 12345     | Enter test result  | Sucess       | 2022-08-02 12:01:01  | Never                 |
And test records now contain 
| Issue ID  | Result   | Date                 | Comment      |
| 12345     | Success  | 2022-08-02 12:01:01  | Works great  |


