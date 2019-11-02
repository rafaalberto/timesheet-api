Feature: Position
    These are tests cases in order to create, update, delete and find positions

    Scenario: Create a new position successfully
        Given the following position data
        | Title    |
        | Analyst  |
        When I attempt to create a new one
        Then I should to receive status "201"
