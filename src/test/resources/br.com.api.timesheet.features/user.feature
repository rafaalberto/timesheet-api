@test
Feature: User
  Scenario: Creating a user
      When I request to create a user
      Then I should see a user created successfully

  Scenario: Updating a user
      Given I have a user stored with username "rafaalberto"
      When I request to update this user with username to "usertest"
      Then I should see user updated successfully

  Scenario: Deleting a user
      Given I have a user stored with username "usertest"
      When I request to delete this user
      Then I should see a user deleted successfully
