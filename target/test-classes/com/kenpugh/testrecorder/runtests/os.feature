Feature:  OS


  Scenario: Set Environment Variable
    When environment variable is set
      | MyConfigurationFileName | Filename |
    Then environment variable is now
      | MyConfigurationFileName | Filename |

  Scenario: Set Environment Variable
    When environment variable is set
      | MyConfigurationFileName | configuration.txt |
    Then environment variable is now
      | MyConfigurationFileName | configuration.txt |
