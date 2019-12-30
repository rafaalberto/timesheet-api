Feature: Position
    These are tests cases in order to create, update and delete positions

    Background:
        Given the following position data stored
        | Id  | Title              |
        | 1   | Analyst            |
        | 2   | Programmer         |
        | 3   | Quality Assurance  |

    Scenario: Creating a new position successfully
        Given the following position data
        | Title           |
        | Systems Analyst |
        When I attempt to create a new position
        Then I should receive status "201" and message "SYSTEMS ANALYST"

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

    Scenario: Creating a new position with invalid data - position already exists
        Given the following position data
        | Title    |
        | Analyst  |
        When I attempt to create a new position
        Then I should receive status "400" and message "Cargo já cadastrado"

    Scenario: Updating a new position successfully
        Given the following position data
        | Title       |
        | Developer   |
        When I attempt to update a new position with id "2"
        Then I should receive status "200" and message "DEVELOPER"

    Scenario: Updating a new position with invalid data - position doesn't exists
        Given the following position data
        | Title       |
        | Programmer  |
        When I attempt to update a new position with id "999"
        Then I should receive status "400" and message "Cargo não encontrado"

    Scenario: Updating a new position with invalid data - title less than 3 characters
        Given the following position data
        | Title       |
        | Pr          |
        When I attempt to update a new position with id "1"
        Then I should receive status "400" and message "Título do cargo deve ter entre 3 a 50 caracteres"

    Scenario: Deleting a position successfully
        When I attempt to delete a position with id "3"
        Then I should receive status "204" and message ""

    Scenario: Deleting a position with invalid data - position doesn't exists
        When I attempt to delete a position with id "999"
        Then I should receive status "400" and message "Cargo não encontrado"