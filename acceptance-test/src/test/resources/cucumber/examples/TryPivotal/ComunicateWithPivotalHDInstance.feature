@terminal @api
Feature: Communicate with a running pivotal HD instance

  As a web client
  In order to be able to issue commands against a running pivotal HD instance
  I want to be able to communicate via a pivotal HD connection service

  Scenario: Create a new account for communication
    When I request a new pivotal HD account
    Then the service should return status code '200'
    And the service response should include a username and password

  Scenario: Submit a valid command with valid username and password
    Given I have a pivotal HD account
    When I submit the command 'CREATE TABLE Person (name VARCHAR(25),age INT);'
    Then the service should return status code '200'
    And the service response should include a 'success' status message

  Scenario: Submit command without creating an account
    When I submit the command 'CREATE TABLE Person (name VARCHAR(25),age INT);'
    Then the service should return status code '200'
    And the service response should include a 'failure' status message
    And the service response should include a 'apiMessages.userDoesNotExist' error message

  Scenario: Submit a command with incorrect password
    Given I have a pivotal HD account
    When I submit the command 'CREATE TABLE Person (name VARCHAR(25),age INT);' with an incorrect password
    Then the service should return status code '200'
    And the service response should include a 'failure' status message
    And the service response should include a 'apiMessages.userDoesNotExist' error message

  Scenario: Retrieve the configurable content of the tutorial
    Given I request a new tutorial
    Then the service should return status code '200'
    And the service response should include a 'id' with 'hawq' message
    And the service response should include a 'type' with 'info' message
    And the service response should include a 'label' with 'Here we are at the start of the tutorial. Click 'next' to proceed.' message