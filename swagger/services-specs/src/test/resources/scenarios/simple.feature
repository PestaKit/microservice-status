Feature: Simple API of services

  Background:
    Given there is a Services server

  Scenario: add a service
    Given I have a service payload
    When I POST it to the /services endpoint
    Then I receive a 201 status code

  Scenario: try to add an invalid service
    Given I have an invalid service payload
    When I POST it to the /services endpoint
    Then I receive a 400 status code

  Scenario: list the existing services
    When I GET on /services endpoint
    Then I receive a list of services
    Then I receive a 200 status code

  Scenario: adding a service increase the size of the list by one
    Given I recuperate the actual size of the service list
    Given I have a service payload
    When I POST it to the /services endpoint
    Then I receive a 201 status code
    Then The size should be greater by one

