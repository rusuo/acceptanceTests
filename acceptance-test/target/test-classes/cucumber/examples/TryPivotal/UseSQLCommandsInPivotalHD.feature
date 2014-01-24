@terminal
Feature: Test the terminal using sql commands

  Scenario: User enters invalid select command
    Given I am on the Pivotal HD product page
    When I view the interactive training page
    And I submit the command "select *;" to the terminal
    Then the terminal window should show the "terminalMessages.selectWithNoTableSpecified" message

  Scenario: User tries to update values from an append-only table
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I submit the command "create table test (name varchar(25));" to the terminal
    When I submit the command "update test set name='john';" to the terminal
    Then the terminal window should show the "terminalMessages.updateFailed" message

  Scenario: User sends valid command on multiple terminal lines
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I submit the command "create" to the terminal
    And I submit the command "table" to the terminal
    And I submit the command "test1" to the terminal
    And I submit the command "(" to the terminal
    And I submit the command "name varchar(25));" to the terminal
    And the terminal window should show the "terminal.okMessage" message
    When I submit the command "select * from test1;" to the terminal
    Then the terminal window should show the "terminalMessages.emptyTable" message

  Scenario: User tries to create a table that already exists
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I submit the command "create table simpleTable (age int);" to the terminal
    When I submit the command "create table simpleTable (name varchar(20));" to the terminal
    Then the terminal window should show the "terminalMessages.tableAlreadyExists" message

  Scenario: User tries to selects values from a table that was dropped
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I submit the command "create table droppedTable (age int);" to the terminal
    And I submit the command "drop table droppedTable;" to the terminal
    When I submit the command "select * from droppedTable;" to the terminal
    Then the terminal window should show the "terminalMessages.tableDoesNotExist" message

 Scenario: User creates a table, inserts values in the table and selects values from that table
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I submit the command "create table oneValue (name varchar(25));" to the terminal
    And I submit the command "insert into oneValue (name) values ('john');" to the terminal
    When I submit the command "select name from oneValue;" to the terminal
    Then the terminal window should show the "terminalMessages.validQueryOneEntry" message
    And the terminal should show "1" rows returned

 Scenario: User creates a table with multiple columns and selects the values from the table
   Given I am on the Pivotal HD product page
   And I view the interactive training page
   And I submit the command "create table multipleValues (name varchar(20), age int, address varchar(45));" to the terminal
   And I submit the command "insert into multipleValues (name, age) values ('tester1', 25);" to the terminal
   And I submit the command "insert into multipleValues (name, age) values ('tester2', 30);" to the terminal
   When I submit the command "select name from multipleValues order by age;" to the terminal
   Then I should see the command "tester1" in the terminal window
   Then I should see the command "tester2" in the terminal window
   And the terminal should show "2" rows returned

 Scenario: User creates table with multiple values and selects the max value
   Given I am on the Pivotal HD product page
   And I view the interactive training page
   And I submit the command "create table maxValue (name varchar(20), age int, address varchar(45));" to the terminal
   And I submit the command "insert into maxValue (name, age) values ('tester1', 25);" to the terminal
   And I submit the command "insert into maxValue (name, age) values ('tester2', 30);" to the terminal
   When I submit the command "select max(age) from maxValue;" to the terminal
   Then I should see the command "30" in the terminal window






