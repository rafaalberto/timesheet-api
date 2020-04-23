Feature: Position
    These are tests cases to create, update and delete positions

    Scenario: Creating a new position successfully
        When I request to create a position with data
            | Title           |
            | Developer       |
        Then I should receive status "201"
        And I should receive message "DEVELOPER"

    Scenario: Getting error when I try to create a position no name
        When I request to create a position with data
            | Title           |
            |                 |
        Then I should receive status "400"
        And I should receive message "Título do cargo deve ser informado"

    Scenario: Getting error when I try to create a position with title less than 3 characters
        When I request to create a position with data
            | Title           |
            | An              |
        Then I should receive status "400"
        And I should receive message "Título do cargo deve ter entre 3 a 50 caracteres"

    Scenario: Getting error when I try to create a position with title more than 50 characters
        When I request to create a position with data
            | Title                                               |
            | Analystaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa |
        Then I should receive status "400"
        And I should receive message "Título do cargo deve ter entre 3 a 50 caracteres"

    Scenario: Getting error when I try to create a position with title already stored
        When I request to create a position with data
            | Title                                               |
            | Developer       |
        Then I should receive status "400"
        And I should receive message "Cargo já cadastrado"

    Scenario: Updating a position successfully
        Given I already have stored these positions
            | Id   | Title           |
            | 10   | Tester          |
        When I request to update a position with id "10" and data
            | Title                  |
            | Quality Assurance      |
        Then I should receive status "200"
        And I should receive message "QUALITY ASSURANCE"

    Scenario: Getting error when I try to update a position that doesn't exist
        Given I already have stored these positions
            | Id   | Title           |
            | 11   | Agile Master    |
        When I request to update a position with id "999" and data
            | Title                  |
            | Agile Leader           |
        Then I should receive status "400"
        And I should receive message "Cargo não encontrado"

    Scenario: Getting error when I try to update a position with title less than 3 characters
        Given I already have stored these positions
            | Id   | Title           |
            | 12   | Analyst         |
        When I request to update a position with id "12" and data
            | Title                  |
            | An                     |
        Then I should receive status "400"
        And I should receive message "Título do cargo deve ter entre 3 a 50 caracteres"

    Scenario: Deleting a position successfully
        Given I already have stored these positions
            | Id   | Title           |
            | 13   | Programmer      |
        When I request to delete a position with id "13"
        Then I should receive status "204"
        And I should receive message ""

    Scenario: Getting error when I try to delete a position that doesn't exist
        Given I already have stored these positions
            | Id   | Title           |
            | 14   | Product Owner   |
        When I request to delete a position with id "15"
        Then I should receive status "400"
        And I should receive message "Cargo não encontrado"
