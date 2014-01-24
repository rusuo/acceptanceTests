@nitro
Feature: Verify list of actions for a user and verify that the actions are marked as completed in Nitro after finishing a quiz

  Scenario: Non logged in user does not finish the tutorial. No action is triggered in Nitro after he logs in.
    Given I have registered a new account
    And I view the interactive training page
    And I click on Learn more without finishing the tutorial
    When I log in with my account
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "false"

  Scenario: Non logged in user finishes the tutorial. An action is triggered in Nitro after he logs in.
    Given I have registered a new account
    When I complete the tutorial
    And I log in with my account
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "true"

  Scenario: A logged in user does not finished the tutorial. No action is triggered in Nitro.
    Given I have registered a new account
    And I log in with my account
    When I view the interactive training page
    And I click on Learn more without finishing the tutorial
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "false"

  Scenario: A logged in user finishes the tutorial. An action is triggered in Nitro.
    Given I have registered a new account
    When I log in with my account
    And I complete the tutorial
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "true"

  Scenario: A user does not finish the tutorial, then he registers. No action is triggered in Nitro
    Given I view the interactive training page
    And I click on Learn more without finishing the tutorial
    When I am on registration page
    And I create a new account
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "false"

  Scenario: A user finishes the tutorial, then registers. An action is triggered in Nitro.
    Given I complete the tutorial
    When I am on registration page
    And I create a new account
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "true"

 Scenario: A logged in user starts a tutorial but does not finish it. No action is triggered in Nitro.
    Given I have registered a new account
    And I log in with my account
    When I view the interactive training page
    And I start the tutorial
    And I click on Learn more without finishing the tutorial
    Then my user in Nitro should show that the action "TUTORIAL_COMPLETE_EXAMPLE" is "false"

 Scenario: A logged in user finishes a quiz. An action is sent to Nitro, but the mission is not complete.
    Given I have registered a new account
    And I log in with my account
    When I am on the quiz page
    And I complete the quiz "Quiz 001" answering "This is the correct one"
    Then my user in Nitro should show that the action "QUIZ_PASS_EXAMPLE" is "true"
    And missions completed for my user are "0"

 Scenario: A user completes tutorial, registers, then finishes the quiz. Mission completed
    Given I completed the tutorial and I registered
    When I am on the quiz page
    And I complete the quiz "Quiz 001" answering "This is the correct one"
    Then my user in Nitro should show that the action "QUIZ_PASS_001" is "true"
    And missions completed for my user are "1"

 Scenario: A logged in user completes tutorial then finishes the quiz. Mission completed
    Given I am a logged in user and I finished the tutorial
    When I am on the quiz page
    And I complete the quiz "Quiz 001" answering "This is the correct one"
    Then my user in Nitro should show that the action "QUIZ_PASS_001" is "true"
    And missions completed for my user are "1"

 Scenario: A logged in user completes tutorial but fails quiz. Mission not complete
    Given I am a logged in user and I finished the tutorial
    When I am on the quiz page
   And I complete the quiz "Quiz 001" answering "This is the correct one"
   Then my user in Nitro should show that the action "QUIZ_PASS_001" is "false"
   And missions completed for my user are "0"