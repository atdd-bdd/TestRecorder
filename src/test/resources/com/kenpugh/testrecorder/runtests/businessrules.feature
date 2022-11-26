Feature:  Business Rules

  Scenario: Update Test from Test Run
    * Update Test from Test Run
      | Old Last Result | Old Date Last Run        | Old Date Previous Result | Result  | Date Time                | New Last Result | New Date Last Run        | New Date Previous Result |
      | Failure         | Never                    | Never                    | Success | Oct 1, 2022, 12:30:01 AM | Success         | Oct 1, 2022, 12:30:01 AM | Never                    |
      | Success         | Oct 1, 2022, 12:30:01 AM | Never                    | Success | Oct 1, 2022, 12:30:02 AM | Success         | Oct 1, 2022, 12:30:02 AM | Never                    |
      | Success         | Oct 1, 2022, 12:30:02 AM | Never                    | Failure | Oct 1, 2022, 12:30:03 AM | Failure         | Oct 1, 2022, 12:30:03 AM | Oct 1, 2022, 12:30:02 AM |

  Scenario: Apply a Sequence of Test Runs
    Given test exists with
      | Last Result | Date Last Run | Date Previous Result |
      | Failure     | Never         | Never                |
    * Update Test from Test Run Sequence
      | Result  | Date Time                | New Last Result | New Date Last Run        | New Date Previous Result |
      | Success | Oct 1, 2022, 12:30:01 AM | Success         | Oct 1, 2022, 12:30:01 AM | Never                    |
      | Success | Oct 1, 2022, 12:30:02 AM | Success         | Oct 1, 2022, 12:30:02 AM | Never                    |
      | Failure | Oct 1, 2022, 12:30:03 AM | Failure         | Oct 1, 2022, 12:30:03 AM | Oct 1, 2022, 12:30:02 AM |
