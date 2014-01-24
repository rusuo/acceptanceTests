@terminal
Feature: Complete a guided tutorial

  As a customer
  In order to understand the capabilities of pivotalHD
  I want to follow guidance to complete a tutorial

  Scenario: User completes a tutorial by clicking on the commands shown in the tutorial window
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I start the tutorial
    When I click to paste the commands from the tutorial window
    Then I should receive the message that I finished the tutorial

  Scenario: User completes the tutorial by typing the commands shown in the tutorial window
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I start the tutorial
    When I type the commands shown in the tutorial
    Then I should receive the message that I finished the tutorial

  Scenario:A user submits a command that is not on the tutorial. The user is not able to continue with the tutorial
    Given I am on the Pivotal HD product page
    And I view the interactive training page
    And I start the tutorial
    When I submit the command "create table testTable (age int);" to the terminal
    Then I shouldn't be able to continue to my tutorial

