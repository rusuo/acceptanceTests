@authentication
Feature: Maintain user accounts and add new accounts

Scenario: Admin creates a new account in the system
   Given I am logged in as an admin
   And I am on add user page
   When I complete the details for the new account with valid data
   Then I should see the "newUser.successfulAdded" message

Scenario: Newly created user can login and modify their account details
  Given I have an account created for me without admin rights
  When I log in with my account
  And I modify my password
  Then I can login with the new password

Scenario: Admin is able to assign administrative priveleges to an account
   Given I am logged in as an admin
   And I am on add user page
   And I complete the details for the new account with valid data
   When I change the role for the new account to "administrator"
   Then the new account should have admin access

Scenario: Create a test account then delete it
  Given I am logged in as an admin
  And I create a test account
  And The new user can login
  When I log in as an admin from user page
  And I delete the user
  Then the user can't login anymore










