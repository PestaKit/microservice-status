Feature: State of the services in the API

  Background:
    Given there is a Services server

  Scenario: list the services with maintenance state
    When I GET on /services?state=maintenance endpoint
    Then I receive a list of services in maintenance
    Then I receive a 200 status code

  Scenario: list the services with down state
    When I GET on /services?state=down endpoint
    Then I receive a list of downed services
    Then I receive a 200 status code

  Scenario: list the services with up state
    When I GET on /services?state=up endpoint
    Then I receive a list of up services
    Then I receive a 200 status code

  Scenario: list the services with an invalid state parameter
    When I GET on /services?state=toto endpoint
    Then I receive a 404 status code

  Scenario: try to add an service with an invalid state parameter
    Given I have an invalid service with an invalid state parameter
    When I POST it to the /services endpoint
    Then I receive a 400 status code
