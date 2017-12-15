Feature: Simple API of services

  Background:
    Given There is a Services server
    Given I have a flushed database
    Then I populate it with some services

  Scenario: list the existing services
    When I GET on /services endpoint
    Then I receive a list of services
    Then I receive a 200 status code

  Scenario: add a service
    Given I have a service payload
    When I POST it to the /services endpoint
    Then I receive a 201 status code

  Scenario: try to add an invalid service
    Given I have an invalid service payload (address is null)
    When I POST it to the /services endpoint
    Then I receive a 400 status code

  Scenario: adding a service increase the size of the list by one
    Given I recuperate the actual size of the service list
    Given I have a service payload
    When I POST it to the /services endpoint
    Then I receive a 201 status code
    Then The size should be greater by one

  Scenario: try to delete all services with a wrong passphrase
    Given I have a wrong passphrase
    When I DELETE on the /services endpoint
    Then I receive a 403 status code
    When I GET on /services endpoint
    Then I GET a non-empty list
    Then I receive a 200 status code

  Scenario: try to delete all services with the correct passphrase
    Given I have a correct passphrase
    When I DELETE on the /services endpoint
    Then I receive a 200 status code
    When I GET on /services endpoint
    Then I GET an empty list
    Then I receive a 200 status code

