@authentication
Feature: Users are able to register and login with the new accounts

  Scenario: New user registers and sees the full content of the site
    Given I am on registration page
    When I create a new account
    Then I see the full content of the site

  Scenario: Newly created user can login back in the site after registering the new account
    Given I have registered a new account
    When I log in with my account
    Then I see the full content of the site

  Scenario: A newly created user has the 'unverified user' role
    Given I have registered a new account
    When I am logged in as an admin
    And I view the the People management page
    Then the new account has the "unverified user" role

  Scenario: Admin adds the 'verified user' role to a new user. The new user will have now both 'unverified' and 'verified' role
    Given I have registered a new account
    And I am logged in as an admin
    When I view the the People management page
    And I change the role for the new account to "verified user"
    Then the new account has the "unverified user" role
    And the new account has the "verified user" role

  Scenario: Admin can change the role of a new user to 'verified user'. The new user will have only the 'verified user' role
    Given I have registered a new account
    And I am logged in as an admin
    When I view the the People management page
    And I edit the role for the new account to "verified user"
    Then the new account has the "verified user" role

  Scenario: Admin changes the role of a new user to 'admin'. The new account has admin rights
    Given I have registered a new account
    And I am logged in as an admin
    When I view the the People management page
    And I change the role for the new account to "administrator"
    Then the new account should have admin access



