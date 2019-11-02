Feature: Position
    These are tests cases in order to create, update and delete positions

    Scenario: Creating a new position successfully
        Given the following position data
        | Title    |
        | Analyst  |
        When I attempt to create a new position
        Then I should receive status "201" and message "ANALYST"

    Scenario: Creating a new position with invalid data - title not informed
        Given the following position data
        | Title    |
        |          |
        When I attempt to create a new position
        Then I should receive status "400" and message "Título do cargo deve ser informado"

    Scenario: Creating a new position with invalid data - title less than 3 characters
        Given the following position data
        | Title    |
        | An       |
        When I attempt to create a new position
        Then I should receive status "400" and message "Título do cargo deve ter entre 3 a 50 caracteres"

    Scenario: Creating a new position with invalid data - title greater than 3 characters
        Given the following position data
        | Title                                               |
        | Analystaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa |
        When I attempt to create a new position
        Then I should receive status "400" and message "Título do cargo deve ter entre 3 a 50 caracteres"