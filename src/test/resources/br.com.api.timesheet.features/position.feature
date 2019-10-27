Feature: Position
    Test Cases in Order to Create, Update, Delete and Find Positions

    Scenario: Attempt to create a new position successfully
        When I post position data
        Then I should to receive status "201"
