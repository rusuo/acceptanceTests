@restrictions
Feature: Verify page restrictions without creating users

  Scenario: Not logged in users see home page
    Given I am on the Pivotal HD product page
    When I access people page as a non logged in user
    Then I should be deny the access

  Scenario: Admin sees the people management page and the available options
          Admin logs in from pivotal HD product page
    Given I am logged in as an admin
    When I view the the People management page
    Then I should see the list of existing users

  Scenario: Admin sees the people management page and the available options
          Admin logs in from user account page
    Given I log in as an admin from user page
    When I view the the People management page
    Then I should see the list of existing users

  Scenario: Admin does not create a new account in the system due to invalid data
    Given I am logged in as an admin
    And I am on add user page
    When I complete the details for the new account with invalid username
    Then I should see the "newUser.unsuccessfulMessage" message

