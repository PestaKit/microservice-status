Feature: Simple API of services

  Background:
    Given there is a Services server

  Scenario: get an empty list of services
    When I GET on /services endpoint
    Then I receive an empty list of services
    Then I receive a 200 status code

  Scenario: create a service
    Given I have a service payload
    When I POST it to the /services endpoint
    Then I receive a 201 status code

  Scenario: try to create an invalid service
    Given I have an invalid service payload
    When I POST it to the /services endpoint
    Then I receive a 400 status code

  Scenario: list the existing services
    When I GET on /services endpoint
    Then I receive a list of services
    Then I receive a 200 status code
