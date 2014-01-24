@terminal
Feature: Connect to a running pivotal HD instance

  Scenario: User connects to pivotal HD
    Given I am on the Pivotal HD product page
    When I view the interactive training page
    Then I should see a terminal window
    And the terminal window should show the "terminalMessages.terminalConnected" message

  Scenario: User clears terminal window
    Given I am on the Pivotal HD product page
    When I view the interactive training page
    And I submit the command "clear" to the terminal
    Then the terminal window should contain '0' messages

  Scenario: User views help
    Given I am on the Pivotal HD product page
    When I view the interactive training page
    And I submit the command "clear" to the terminal
    And I submit the command "help" to the terminal
    Then the terminal window should show the "terminalMessages.helpMessage" message

  Scenario: User issues invalid command to terminal without using ';' character
    Given I am on the Pivotal HD product page
    When I view the interactive training page
    And I submit the command "test" to the terminal
    Then the terminal window should show the "terminalMessages.waitingForEndStatement" message

  Scenario: User issues invalid command to terminal using ';' character
    Given I am on the Pivotal HD product page
    When I view the interactive training page
    And I submit the command "test;" to the terminal
    Then the terminal window should show the "terminalMessages.invalidCommand" message

  Scenario: User enters invalid command on multiple lines and types help in the middle of the invalid command
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I submit the command "dsadda" to the terminal
    And I submit the command "dsada" to the terminal
    When I submit the command "help" to the terminal
    Then I should see the command "dsadda" in the terminal window
