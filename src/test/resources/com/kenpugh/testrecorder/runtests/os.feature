Feature:  OS


  Scenario: Set Environment Variable
    When environment variable is set
      | MyConfigurationFileName | XXXX |
    Then environment variable is now
      | MyConfigurationFileName | XXXX |

  Scenario: Set Environment Variable
    When environment variable is set
      | MyConfigurationFileName | C:\\Users\\user\\IdeaProjects\\TestRecorder\\target\\configuration.txt |
    Then environment variable is now
      | MyConfigurationFileName | C:\\Users\\user\\IdeaProjects\\TestRecorder\\target\\configuration.txt |
