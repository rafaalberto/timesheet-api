@test
Feature: User
  Scenario: Creating a user successfully
      When I request to create a user
      Then I should see a user created successfully

  Scenario: Updating a user successfully
      Given I have a user stored with username "rafaalberto"
      When I request to update this user with username to "usertest"
      Then I should see user updated successfully

  Scenario: Deleting a user successfully
      Given I have a user stored with username "usertest"
      When I request to delete this user
      Then I should see a user deleted successfully
