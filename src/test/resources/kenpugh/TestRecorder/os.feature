Feature:  OS

  @doing
  Scenario: Set Environment Variable
    When environment variable is set
    | MyConfigurationFileName |  XXXX |
    Then environment variable is now
      | MyConfigurationFileName |  XXXX |

  Scenario: Set Environment Variable
    When environment variable is set
      | MyConfigurationFileName |  C:\\Users\\KenV1\\IdeaProjects\\TestRecorder\\target\\configuration.txt |
    Then environment variable is now
      | MyConfigurationFileName |  C:\\Users\\KenV1\\IdeaProjects\\TestRecorder\\target\\configuration.txt |
